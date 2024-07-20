package com.yan.yanchat.common.infrastructure.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @Author: sixcolor
 * @Date: 2024-02-17
 * @Description:
 */
public class SpringELUtils {

    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    public static String getMethodKey(Method method) {
        return method.getDeclaringClass() + "#" + method.getName();
    }

    public static String parseEl(Method method, Object[] args, String springEl) {
        String[] params = Optional.ofNullable(PARAMETER_NAME_DISCOVERER.getParameterNames(method))
                .orElse(new String[]{});
        //el解析需要的上下文对象
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) {
            //所有参数都作为原材料放进去
            context.setVariable(params[i], args[i]);
        }
        Expression expression = PARSER.parseExpression(springEl);
        return expression.getValue(context, String.class);
    }
}
