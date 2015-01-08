//import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import domains.Item;
import domains.Pos;

import domains.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
 * Created by Administrator on 2014/12/28.
 */



public class PosTest {

    /**
     * *********************************************测试是会员有活动有打折有vip折扣****************************************************
     */
    @Test
    public void test1() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao3.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin4.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0004"
                        + "   会员积分：210\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:6瓶,单价:3.00(元),小计:15.00(元)\n"
                        + "名称:雪碧,数量:2瓶,单价:3.00(元),小计:3.00(元)\n"
                        + "名称:电池,数量:1个,单价:2.00(元),小计:1.60(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品:\n"
                        + "名称:可口可乐,数量:1瓶\n"
                        + "----------------------\n"
                        + "总计:19.60(元)\n"
                        + "节省:6.40(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * *********************************************是vip用户只有活动****************************************************
     */
    @Test
    public void test2() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao4.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin2.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0001   "
                        + "会员积分：525\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:4瓶,单价:3.00(元),小计:9.00(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品:\n"
                        + "名称:可口可乐,数量:1瓶\n"
                        + "----------------------\n"
                        + "总计:9.00(元)\n"
                        + "节省:3.00(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * ***************************************************会员积分小于200时的积分变化******************************************************
     */
    @Test
    public void test3() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao2.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin2.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0003   "
                        + "会员积分：16\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:3瓶,单价:3.00(元),小计:6.00(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品:\n"
                        + "名称:可口可乐,数量:1瓶\n"
                        + "----------------------\n"
                        + "总计:6.00(元)\n"
                        + "节省:3.00(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * ******************************************积分小于200时有活动*********************************************************
     */
    @Test
    public void test4() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao1.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin1.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0003   "
                        + "会员积分：17\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:雪碧,数量:3瓶,单价:3.00(元),小计:7.20(元)\n"
                        + "名称:可口可乐,数量:1瓶,单价:3.00(元),小计:3.00(元)\n"
                        // +"----------------------\n"
                        // +"挥泪赠送商品:\n"
                        // +"名称:可口可乐,数量:1瓶\n"
                        + "----------------------\n"
                        + "总计:10.20(元)\n"
                        + "节省:1.80(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * ******************************************************积分在200到五百之间积分的变化是否正确*******************************************************
     */
    @Test
    public void test5() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\liebiao\\liebiao1.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\ITEM\\suoyin1.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0004   "
                        + "会员积分：213\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:5瓶,单价:3.00(元),小计:15.00(元)\n"
                        + "名称:雪碧,数量:2瓶,单价:3.00(元),小计:6.00(元)\n"
                        + "名称:电池,数量:1个,单价:2.00(元),小计:1.60(元)\n"
                        + "----------------------\n"
                        + "总计:22.60(元)\n"
                        + "节省:0.40(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * *******************************************************积分大于五百时积分的变化*************************************************************
     */
    @Test
    public void test6() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao4.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin2.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0001   "
                        + "会员积分：525\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:4瓶,单价:3.00(元),小计:9.00(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品:\n"
                        + "名称:可口可乐,数量:1瓶\n"
                        + "----------------------\n"
                        + "总计:9.00(元)\n"
                        + "节省:3.00(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * ************************************当会员属于vip时会员折扣是否有效*************************************************
     */

    @Test
    public void test7() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\liebiao\\liebiao2.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\ITEM\\suoyin2.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0003   "
                        + "会员积分：15\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:1瓶,单价:3.00(元),小计:1.50(元)\n"
                        + "----------------------\n"
                        + "总计:1.50(元)\n"
                        + "节省:1.50(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * *****************************************************会员折扣是否与普通折扣叠加*********************************************************
     */
    @Test
    public void test8() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\liebiao\\liebiao3.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\ITEM\\suoyin3");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0003   "
                        + "会员积分：15\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:1瓶,单价:3.00(元),小计:0.75(元)\n"
                        + "----------------------\n"
                        + "总计:0.75(元)\n"
                        + "节省:2.25(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * ********************************************无折扣时vip折扣是否有效**********************************************************
     */
    @Test
    public void test9() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\liebiao\\liebiao3.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\ITEM\\suoyin4.txt");
        String AllUser = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input2\\USER\\user.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Map<String, User> map1 = Pos.user(AllUser);
        Pos.gettotal2(liebiao, map);
        User user = new User();
        user = Pos.getUser(liebiao, map1);
        String actualShoppingList = Pos.print(map, user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0003   "
                        + "会员积分：15\n"
                        + "----------------------\n"
                        + "打印时间:"
                        + format.format(Calendar.getInstance().getTime()) + "\n"
                        + "----------------------\n"
                        + "名称:可口可乐,数量:1瓶,单价:3.00(元),小计:1.50(元)\n"
                        + "----------------------\n"
                        + "总计:1.50(元)\n"
                        + "节省:1.50(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    /**
     * ************************************************非会员购物******************************************************
     */
    @Test
    public void test10() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao3.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin3.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Pos.gettotal2(liebiao, map);
        String actualShoppingList = Pos.print(map);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"打印时间:"+format.format(Calendar.getInstance().getTime())+"\n"
                        +"----------------------\n"
                        + "名称:可口可乐,数量:5瓶,单价:3.00(元),小计:15.00(元)\n"
                        + "名称:雪碧,数量:2瓶,单价:3.00(元),小计:6.00(元)\n"
                        + "名称:电池,数量:1个,单价:2.00(元),小计:1.60(元)\n"
                        + "----------------------\n"
                        + "总计:22.60(元)\n"
                        + "节省:0.40(元)\n"
                        + "**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}