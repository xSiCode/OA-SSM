package util;

import com.th.bean.User;
import com.th.utils.RandomValueUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @Class : RandomValueTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/9 14:32
 * @Version : 1.0
 */
public class RandomValueTest {

    @Test
    public void getEmail(){
        for (int i=0;i<1000;i++){
            String email = RandomValueUtil.getEmail();
            System.out.println(i+":"+email);
        }

    }
    @Test
    public void getTel(){
        for (int i=0;i<1000;i++){
            String tel = RandomValueUtil.getTel();
            System.out.println(i+":"+tel);
        }

    }
    @Test
    public void getName(){
        for (int i=0;i<1000;i++){
            String name= RandomValueUtil.getChineseName();
            System.out.println(i+":"+name);
        }

    }

    @Test
    public void getIdCard(){
        for (int i=0;i<1000;i++){
            String birth =RandomValueUtil.getBirth();
            String sex  =RandomValueUtil.getSex();
            String idCard=RandomValueUtil.getIdCard(birth,sex);
            String email =RandomValueUtil.getEmail();

            System.out.println(i+":\t"+birth+":\t"+sex+":\t"+idCard+":\t"+email);
        }

    }

    @Test
    public void insetUser() throws ParseException {
        String password="";
        String role="";
        String name="";
        String tel="";
        String email="";
        String sex="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");;   //1997-11-08
        String birth="";
        Date birthDate=null;
        String idcard="";


        User user =new User();
        for(int i=1000;i<1999;i++){
            password=RandomValueUtil.getPassword();
            role=RandomValueUtil.getRole();
            name=RandomValueUtil.getChineseName();
            tel=RandomValueUtil.getTel();
            email=RandomValueUtil.getEmail();
            sex=RandomValueUtil.getSex();
            birth=RandomValueUtil.getBirth();  // 1997-11-08
            idcard=RandomValueUtil.getIdCard(birth,sex);
            //
            user.setUserId(i);
            user.setUserPassword(password);
            user.setUserRole(role);
            user.setUserName(name);
            user.setUserTel(tel);
            user.setUserEmail(email);
            user.setUserSex(sex);
            //日期有点特俗  string:yyMMss  ---> date ;

            birthDate=sdf.parse(birth);
            //
          //  user.setUserBirth( birthDate );
            user.setUserIdCard(idcard);
            //
            System.out.println(user);

        }




    }
    @Test
    public void testExer() throws ParseException {
       // String birth = "20200908";


        String sex="";
        String birth="";
        String birthInner="";
        String idcard="";
        SimpleDateFormat sdf = null;
        Date birthDate=null;

        sex=RandomValueUtil.getSex();
        birth=RandomValueUtil.getBirth();  // 1997-11-08
        idcard=RandomValueUtil.getIdCard(birthInner,sex);

        User user =new User();
        for(int i=1000;i<1099;i++){
            user.setUserId(i);
            user.setUserSex(sex);
            //日期有点特俗  string:yyMMss  ---> date ;
            sdf= new SimpleDateFormat("yyyy-MM-dd");
            birthDate=sdf.parse(birth);
            //
        //    user.setUserBirth( birthDate );
            user.setUserIdCard(idcard);
            //
            System.out.println(user);

        }

    }

    @Test
    public void timecrud(){
        //jdk8
        LocalDate localDate =  LocalDate.of(1997,11,8);
        System.out.println(localDate);
        DateTimeFormatter formatter2 =DateTimeFormatter.ofPattern("yyyyMMdd");
        String str2 = formatter2.format(localDate);
        System.out.println(str2);
        String str3= "19920102";
        LocalDate l2= LocalDate.parse(str3, formatter2);
        TemporalAccessor accessor= formatter2.parse(str3);
        System.out.println(l2);
        System.out.println(accessor);
    }
}
