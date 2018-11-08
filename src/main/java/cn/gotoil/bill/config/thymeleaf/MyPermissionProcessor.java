package cn.gotoil.bill.config.thymeleaf;

import cn.gotoil.bill.exception.PermissionError;
import cn.gotoil.bill.exception.SecureException;
import cn.gotoil.bill.model.BaseAdminUser;
import cn.gotoil.bill.web.helper.ServletRequestHelper;

import com.google.common.base.Splitter;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;
import java.util.Optional;

import static cn.gotoil.bill.config.thymeleaf.ThymeleafFacade.getRawValue;


/**
 * Created by think on 2017/9/13.
 * <p>
 * hasAnyPermission
 */
public class MyPermissionProcessor extends AbstractAttributeTagProcessor {


    public MyPermissionProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                "hasPermission",         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                200,        // Precedence (inside dialect's own precedence)
                false);             // Remove the matched attribute afterwards
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        //"admin,yyuser"
        final String rawValue = getRawValue(tag, attributeName);

        List<String> needPermissions = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(rawValue);

        Object o = ServletRequestHelper.httpServletRequest().getSession().getAttribute("UserInfo");
        //如果发现用户未登录
        Optional.ofNullable(o).map(m -> m).orElseThrow(() -> new SecureException(PermissionError.Need_Login));
        BaseAdminUser adminUser = null;
        if (o instanceof BaseAdminUser) {
            adminUser = (BaseAdminUser) o;
        } else {
            throw new SecureException(PermissionError.Need_Login);
        }

        String hasPermissionStr = adminUser.getPermissionStr();
        List<String> hasPermissionList =
                Splitter.on(',').trimResults().omitEmptyStrings().splitToList(hasPermissionStr);

        boolean flag = false;
        for (String s : needPermissions) {
            //只要有1个这个权限点 就认为条件成立
            if (hasPermissionList.contains(s)) {
                flag = true;
                break;
            }
        }

        //标签名
        final String elementCompleteName = tag.getElementCompleteName();
        //创建模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        if (!flag) {
/*            //添加模型 标签
            model.remove(modelFactory.createOpenElementTag(elementCompleteName));
            //添加模型 标签
            model.add(modelFactory.createCloseElementTag(elementCompleteName));*/
            //替换页面标签
            structureHandler.replaceWith(model, false);
        }


    }
}
