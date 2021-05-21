package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * @Class : MatterGenerator
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/10 09:55
 * @Version : 1.0
 */
public class LeaveGeneratorTest {
    @Test
    public void leaveGenerator() {
        //1全局配置
        /*
         *作者，生成路径，再次生成文件时是否覆盖，生成主键策略，service接口的首字母是否位i,生成基本属性字段，表字段
         *  */
        GlobalConfig config = new GlobalConfig();
        config.setAuthor("杨天发")// 指定作者
                .setOutputDir("D:\\OA-Vue-SSM\\ssm\\ForairAcademy\\src\\main\\java") //生成文件路径
                .setFileOverride(true) // 指定文件覆盖
                .setIdType(IdType.AUTO) // 设置主键自增策略
                .setServiceName("%sService") // 设置生成的services接口的名字的首字母是否为I
                .setBaseResultMap(true) // 基本的字段映射
                .setBaseColumnList(true); // 基本的sql片段
        //2，数据源 配置
        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/oa-forairacademy")
                .setUsername("root")
                .setPassword("xsixsi");
        //3,策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true) //全局大写命名
                .setNaming(NamingStrategy.underline_to_camel) // 数据库字段下划线转驼峰命令策略
                .setColumnNaming(NamingStrategy.underline_to_camel) // 表字段下划线转驼峰命令策略
                .setTablePrefix("t_") // 设置表前缀
                .setInclude("t_leave"); // 设置需要生成的表  可多个，即 一次性生成
                //.setInclude("t_meeting_room"); // 设置需要生成的表  可多个，即 一次性生成
        // 4.包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.th") // 设置父包
                .setEntity("entity")
                .setController("controller")
                .setMapper("dao")
                .setXml("dao.mapper")
                .setService("service");
        // 5. 开始生成代码
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);
        autoGenerator.execute();

    }

}
