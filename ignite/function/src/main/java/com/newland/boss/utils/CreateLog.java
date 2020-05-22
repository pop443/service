package com.newland.boss.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by xz on 2020/5/11.
 */
public class CreateLog {

    public static void main(String[] args) {
        for (int i = 1; i < 7; i++) {
            String config = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "\n" +
                    "<log4j:configuration xmlns:log4j=\"http://jakarta.apache.org/log4j/\" debug=\"false\">\n" +
                    "    <!--\n" +
                    "    <appender name=\"CONSOLE\" class=\"org.apache.log4j.ConsoleAppender\">\n" +
                    "        <param name=\"Target\" value=\"System.out\"/>\n" +
                    "\n" +
                    "        <param name=\"Threshold\" value=\"DEBUG\"/>\n" +
                    "\n" +
                    "        <layout class=\"org.apache.log4j.PatternLayout\">\n" +
                    "            <param name=\"ConversionPattern\" value=\"[%d{ISO8601}][%-5p][%t][%c{1}] %m%n\"/>\n" +
                    "        </layout>\n" +
                    "\n" +
                    "        <filter class=\"org.apache.log4j.varia.LevelRangeFilter\">\n" +
                    "            <param name=\"levelMin\" value=\"DEBUG\"/>\n" +
                    "            <param name=\"levelMax\" value=\"WARN\"/>\n" +
                    "        </filter>\n" +
                    "    </appender>\n" +
                    "    -->\n" +
                    "\n" +
                    "    <!--\n" +
                    "        Logs all ERROR messages to console.\n" +
                    "    -->\n" +
                    "    <appender name=\"CONSOLE_ERR\" class=\"org.apache.log4j.ConsoleAppender\">\n" +
                    "        <!-- Log to STDERR. -->\n" +
                    "        <param name=\"Target\" value=\"System.err\"/>\n" +
                    "\n" +
                    "        <!-- Log from ERROR and higher (change to WARN if needed). -->\n" +
                    "        <param name=\"Threshold\" value=\"INFO\"/>\n" +
                    "\n" +
                    "        <!-- The default pattern: Date Priority [Category] Message\\n -->\n" +
                    "        <layout class=\"org.apache.log4j.PatternLayout\">\n" +
                    "            <param name=\"ConversionPattern\" value=\"%d [%t] %-5p [%c] - %m%n\"/>\n" +
                    "        </layout>\n" +
                    "    </appender>\n" +
                    "\n" +
                    "    <!--\n" +
                    "        Logs all output to specified file.\n" +
                    "        By default, the logging goes to IGNITE_HOME/work/log folder\n" +
                    "    -->\n" +
                    "    <appender name=\"FILE\" class=\"org.apache.ignite.logger.log4j.Log4JDailyRollingFileAppender\">\n" +
                    "        <param name=\"Threshold\" value=\"INFO\"/>\n" +
                    "        <param name=\"File\" value=\"/bosslog/ignite/log/log"+i+"/ignite.log\"/>\n" +
                    "        <param name=\"Append\" value=\"true\"/>\n" +
                    "        <param name=\"MaxFileSize\" value=\"10MB\"/>\n" +
                    "        <param name=\"MaxBackupIndex\" value=\"10\"/>\n" +
                    "        <layout class=\"org.apache.log4j.PatternLayout\">\n" +
                    "            <param name=\"ConversionPattern\" value=\"%d [%t] %-5p [%c] - %m%n\"/>\n" +
                    "        </layout>\n" +
                    "    </appender>\n" +
                    "\n" +
                    "        <appender name=\"FILE_ERROR\" class=\"org.apache.ignite.logger.log4j.Log4JDailyRollingFileAppender\">\n" +
                    "        <param name=\"Threshold\" value=\"INFO\"/>\n" +
                    "        <param name=\"File\" value=\"/bosslog/ignite/log/log"+i+"/ignite_error.log\"/>\n" +
                    "        <param name=\"Append\" value=\"true\"/>\n" +
                    "        <param name=\"MaxFileSize\" value=\"10MB\"/>\n" +
                    "        <param name=\"MaxBackupIndex\" value=\"10\"/>\n" +
                    "        <layout class=\"org.apache.log4j.PatternLayout\">\n" +
                    "            <param name=\"ConversionPattern\" value=\"%d [%t] %-5p [%c] - %m%n\"/>\n" +
                    "        </layout>\n" +
                    "    </appender>\n" +
                    "    \n" +
                    "        <!--\n" +
                    "        Uncomment to disable courtesy notices, such as SPI configuration\n" +
                    "        consistency warnings.\n" +
                    "    -->\n" +
                    "    <!--\n" +
                    "    <category name=\"org.apache.ignite.CourtesyConfigNotice\">\n" +
                    "        <level value=\"OFF\"/>\n" +
                    "    </category>\n" +
                    "    -->\n" +
                    "\n" +
                    "    <category name=\"org.springframework\">\n" +
                    "        <level value=\"WARN\"/>\n" +
                    "    </category>\n" +
                    "\n" +
                    "    <category name=\"org.eclipse.jetty\">\n" +
                    "        <level value=\"WARN\"/>\n" +
                    "    </category>\n" +
                    "\n" +
                    "    <!--\n" +
                    "        Avoid warnings about failed bind attempt when multiple nodes running on the same host.\n" +
                    "    -->\n" +
                    "    <category name=\"org.eclipse.jetty.util.log\">\n" +
                    "        <level value=\"ERROR\"/>\n" +
                    "    </category>\n" +
                    "\n" +
                    "    <category name=\"org.eclipse.jetty.util.component\">\n" +
                    "        <level value=\"ERROR\"/>\n" +
                    "    </category>\n" +
                    "\n" +
                    "    <category name=\"com.amazonaws\">\n" +
                    "        <level value=\"WARN\"/>\n" +
                    "    </category>\n" +
                    "\n" +
                    "    <!-- Default settings. -->\n" +
                    "    <root>\n" +
                    "        <!-- Print out all info by default. -->\n" +
                    "        <level value=\"INFO\"/>\n" +
                    "\n" +
                    "        <!-- Uncomment to enable logging to console. -->\n" +
                    "        <!--\n" +
                    "        <appender-ref ref=\"CONSOLE\"/>\n" +
                    "        -->\n" +
                    "\n" +
                    "        <appender-ref ref=\"CONSOLE_ERR\"/>\n" +
                    "        <appender-ref ref=\"FILE\"/>\n" +
                    "                <appender-ref ref=\"FILE_ERROR\"/>\n" +
                    "    </root>\n" +
                    "</log4j:configuration>" ;
            String basePath = "F:\\work\\boss\\2020\\ignite_test\\igntie测试用例及结果\\结果\\第四版\\环境\\配置无持久化";
            FileWriter writer;
            try {
                writer = new FileWriter(basePath+"\\node-"+i+"-log4j.xml");
                writer.write(config);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("ok"+i);
        }

    }
}
