package next.common.web;


import com.google.common.collect.Lists;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.rc.ControllerHandlerAdapter;
import core.rc.HandlerAdapter;
import core.rc.HandlerExecutionHandlerAdapter;
import core.rc.HandlerMapping;
import core.rc.LegacyHandlerMapping;
import core.rc.filter.ExecutionRunner;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import next.common.error.DomainException;

@NoArgsConstructor
@Slf4j
public class DispatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final List<HandlerMapping> mappings = Lists.newArrayList();
    private final List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
    private Object[] basePackages;
    private HandlerMapping handlerMapping;

    public DispatchServlet(Object... basePackages) {
        this.basePackages = basePackages;
    }

    // 나중에 전체 리팩터링할때 수정할 예정
    public DispatchServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void init() {

        final var legacyHandlerMapping = new LegacyHandlerMapping();
        mappings.add(handlerMapping);
        mappings.add(legacyHandlerMapping);

        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());

        ExecutionRunner.getInstance().initialize(basePackages);
        log.info("initialize method execution setup");

    }

    @SneakyThrows
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Object handler = getHandler(request);
        final var requestUrl = request.getRequestURI();
        log.info("Method : {}, Request URL: {}", request.getMethod(), requestUrl);

        try {
            ModelAndView modelAndView = execute(handler, request, response);
            if (modelAndView != null) {
                modelAndView.getView().render(modelAndView.getModel(), request, response);
            }
        } catch (DomainException domainException) {
            log.info("{}", domainException.getMessage());
            new JspView("redirect: /user/loginForm").render(null, request, response);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } catch (Throwable e) {
            log.error("Exception : {} ", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest request) {
        return mappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findAny().orElse(null);
    }

    private ModelAndView execute(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var findHandlerAdapter = handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findAny();
        if (findHandlerAdapter.isPresent()) {
            return findHandlerAdapter.get().handle(request, response, handler);
        }
        return null;
    }
}

