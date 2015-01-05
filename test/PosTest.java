//import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import domains.Item;
import domains.Pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
 * Created by Administrator on 2014/12/28.
 */



public class PosTest {
    /************************************************一件商品*****************************************************/
    @Test
    public void test1() throws Exception {
        String inputStr = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\input2.txt");
        List<Item> aa = Pos.getItems(inputStr);
        Map<String, Item> map = Pos.gettotal1(aa);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"名称:电池,数量:1个,单价:2.00(元),小计:1.60(元)\n"
                        +"----------------------\n"
                        +"总计:1.60(元)\n"
                        +"节省:0.40(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    /************************************************多种不一样不同数目的商品无折扣*****************************************************/
    @Test
    public void test2() throws Exception {
        String inputStr = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\input1.txt");
        List<Item> aa = Pos.getItems(inputStr);
        Map<String, Item> map = Pos.gettotal1(aa);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"名称:可口可乐,数量:5瓶,单价:3.00(元),小计:15.00(元)\n"
                        +"名称:雪碧,数量:2瓶,单价:3.00(元),小计:6.00(元)\n"
                        +"名称:电池,数量:1个,单价:2.00(元),小计:2.00(元)\n"
                        +"----------------------\n"
                        +"总计:23.00(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    /*****************************************************多种不一样不同数目的商品有折扣************************************************/
    @Test
    public void test7() throws Exception {
        String inputStr = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\input4.txt");
        List<Item> aa = Pos.getItems(inputStr);
        Map<String, Item> map = Pos.gettotal1(aa);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"名称:可口可乐,数量:5瓶,单价:3.00(元),小计:12.00(元)\n"//8折
                        +"名称:雪碧,数量:2瓶,单价:3.00(元),小计:4.20(元)\n"//7折
                        +"名称:电池,数量:1个,单价:2.00(元),小计:2.00(元)\n"
                        +"----------------------\n"
                        +"总计:18.20(元)\n"
                        +"节省:4.80(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    /*****
    /************************************************列表索引多种商品有折扣无优惠*****************************************************/
    @Test
    public void test3() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao3.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin3.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Pos.gettotal2(liebiao, map);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"名称:可口可乐,数量:5瓶,单价:3.00(元),小计:15.00(元)\n"
                        +"名称:雪碧,数量:2瓶,单价:3.00(元),小计:6.00(元)\n"
                        +"名称:电池,数量:1个,单价:2.00(元),小计:1.60(元)\n"
                        +"----------------------\n"
                        +"总计:22.60(元)\n"
                        +"节省:0.40(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    /************************************************列表索引多种不一样不同数目的商品有优惠无折扣*****************************************************/
    @Test
    public void test4() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao4.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin4.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Pos.gettotal2(liebiao, map);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"打印时间:2014年08月04日 08:09:05\n"
                        +"----------------------\n"
                        +"名称:可口可乐,数量:3瓶,单价:3.00(元),小计:6.00(元)\n"
                        +"----------------------\n"
                        +"挥泪赠送商品:\n"
                        +"名称:可口可乐,数量:1瓶\n"
                        +"----------------------\n"
                        +"总计:6.00(元)\n"
                        +"节省:3.00(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
/******************************************************商品数目大于4时的赠送商品数*******************************************************/
    @Test
    public void test5() throws Exception {
        String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao2.txt");
        String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin2.txt");
        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Pos.gettotal2(liebiao, map);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"打印时间:2014年08月04日 08:09:05\n"
                        +"----------------------\n"
                        +"名称:可口可乐,数量:4瓶,单价:3.00(元),小计:9.00(元)\n"
                        +"----------------------\n"
                        +"挥泪赠送商品:\n"
                        +"名称:可口可乐,数量:1瓶\n"
                        +"----------------------\n"
                        +"总计:9.00(元)\n"
                        +"节省:3.00(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    /*********************************************输入为空时**********************************************************/
    @Test
    public void test6() throws Exception {
        String inputStr = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\input3.txt");
        List<Item> aa = Pos.getItems(inputStr);
        Map<String, Item> map = Pos.gettotal1(aa);
        String actualShoppingList = Pos.print(map);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"

                        +"----------------------\n"
                        +"总计:0.00(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    /*********************************************有优惠有折扣但是优惠商品数额为1**********************************************************/
    @Test
    public void test8() throws Exception {
       String liebiao = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\liebiao1.txt");
       String suoyin = Pos.readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin1.txt");

       // Map<String, Item> map = Pos.getSuoyin(suoyin);
       // Pos.gettotal2(liebiao, map);
       // String actualShoppingList = Pos.print(map);

        Map<String, Item> map = Pos.getSuoyin(suoyin);
        Pos.gettotal2(liebiao, map);
        String actualShoppingList = Pos.print(map);
        // then
        String expectedShoppingList =                                               //由于后来修改的需求，所以打印时间只能在有
                "***商店购物清单***\n"
                        //+"打印时间:2014年08月04日 08:09:05\n"
                       // +"----------------------\n"
                        +"名称:雪碧,数量:3瓶,单价:3.00(元),小计:7.20(元)\n"
                        +"名称:可口可乐,数量:1瓶,单价:3.00(元),小计:3.00(元)\n"
                       // +"----------------------\n"
                       // +"挥泪赠送商品:\n"
                       // +"名称:可口可乐,数量:1瓶\n"
                        +"----------------------\n"
                        +"总计:10.20(元)\n"
                        +"节省:1.80(元)\n"
                        +"**********************";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}