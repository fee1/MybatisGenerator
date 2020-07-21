package org.mybatis.generator.plugins.rename;


import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

public class RenameSqlMapperPlugin extends PluginAdapter {
    private String searchString;
    private String replaceString;
    private Pattern pattern;


    public RenameSqlMapperPlugin() {
    }

    public boolean validate(List<String> warnings) {
        this.searchString = this.properties.getProperty("searchString");
        this.replaceString = this.properties.getProperty("replaceString");
        boolean valid = StringUtility.stringHasValue(this.searchString) && StringUtility.stringHasValue(this.replaceString);
        if (valid) {
            this.pattern = Pattern.compile(this.searchString);
        } else {
            if (!StringUtility.stringHasValue(this.searchString)) {
                warnings.add(Messages.getString("ValidationError.18", "RenameExampleClassPlugin", "searchString"));
            }

            if (!StringUtility.stringHasValue(this.replaceString)) {
                warnings.add(Messages.getString("ValidationError.18", "RenameExampleClassPlugin", "replaceString"));
            }
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String oldType = introspectedTable.getMyBatis3XmlMapperFileName();
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);

        introspectedTable.setMyBatis3XmlMapperFileName(oldType);
    }
}