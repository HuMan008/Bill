package cn.gotoil.bill.config.thymeleaf;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import static cn.gotoil.bill.config.thymeleaf.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static cn.gotoil.bill.config.thymeleaf.ThymeleafFacade.getRawValue;


/**
 * Created by think on 2017/7/5.
 */
public class MyNumberProcessor extends AbstractAttributeTagProcessor {

    private static final String DELIMITER = ",";

    private static final String ATTR_NAME = "number";
    private static final int PRECEDENCE = 100;


    private static final String F2Y = "moneyF2y"; //金额分转元保留2位小数

    public MyNumberProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's own precedence)
                true);             // Remove the matched attribute afterwards
    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {

        final String rawValue = getRawValue(tag, attributeName); //获取标签内容表达式
        String method = null;
        String exper = null;
        if (StringUtils.isNotEmpty(rawValue)) {
            method = rawValue.split(":")[0];  //获取类型
            exper = rawValue.split(":")[1]; //获取表达式
        }
        //通过IStandardExpression 解析器 解析表达式获取参数
        final List<String> values = evaluateAsStringsWithDelimiter(context, exper, DELIMITER);
        final String elementCompleteName = tag.getElementCompleteName(); //标签名
        //创建模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        //添加模型 标签
        model.add(modelFactory.createOpenElementTag(elementCompleteName));
        for (String value : values) {
            //创建 html5标签 文本返回数据
            if (F2Y.equalsIgnoreCase(method)) {
                try {
                    double n = NumberFormat.getInstance().parse(value).doubleValue();
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5(formatMoney(n))));
                } catch (ParseException e) {
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5("0")));
                }

            }
        }
        //添加模型 标签
        model.add(modelFactory.createCloseElementTag(elementCompleteName));
        //替换页面标签
        structureHandler.replaceWith(model, false);
    }

    private String formatMoney(double value) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        String v = df.format(value / 100);//返回的是String类型的
        return v;
    }


}
