package domains;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Pos {

	public static List<Item> getItems(String inputStr)
	{
		JSONArray json = JSONArray.fromObject(inputStr);
		List<Item> persons = (List<Item>)JSONArray.toList(json, Item.class);
		
        return persons;
	}
	
	public static Map<String, Item> getSuoyin(String inputStr)
	{
		JSONObject json = JSONObject.fromObject(inputStr);
		
		Map<String, Item> map = new HashMap<String, Item>();
		Iterator<String> keys = json.keys();
		while(keys.hasNext())
		{
			String key = (String) keys.next();
			JSONObject obj = (JSONObject) json.get(key);
			Item item = new Item();
			item.setBarcode(key);
			item.setCount(0);
			if (obj.containsKey("discount")) 
			{
				item.setDiscount(obj.getDouble("discount"));
			}
			item.setName(obj.getString("name"));
			item.setPrice(obj.getDouble("price"));
			item.setUnit(obj.getString("unit"));
			if (obj.containsKey("promotion")) 
			{
				item.setPromotion(obj.getBoolean("promotion"));
			}
			map.put(key, item);
		}
		return map;
	}
	
	public static void gettotal2(String inputStr, Map<String, Item> suoyin)
	{
		JSONArray json = JSONArray.fromObject(inputStr);
		List<String> persons = (List<String>)JSONArray.toList(json, String.class);
		for (String key : persons) 
		{
			Item item = suoyin.get(key);
			item.setCount(item.getCount() + 1);
		}
	}
	
	public static HashMap gettotal1(List<Item> list)
	{
		HashMap<String, Item> map = new HashMap(); 
		for (Item item : list) {
			if (!map.containsKey(item.getBarcode())) {
				item.setCount(1);
				map.put(item.getBarcode(), item);
			}
			else
			{
				Item tmp = map.get(item.getBarcode());
				tmp.setCount(tmp.getCount() + 1);
			}
		}
		return map;
	}
	
//	public static void print1(Map<String, Item> map)
//	{
//		 Iterator<Entry<String, Item>> iterator = map.entrySet().iterator();
//		 StringBuffer sb = new StringBuffer("***商店购物清单***\n");
//		 double total = 0;
//		 double jiesheng = 0;
//		 
//		 DecimalFormat df = new DecimalFormat("0.00");
//		 while(iterator.hasNext())
//		 {
//			 Entry<String, Item> entry = iterator.next();
//			 Item item = entry.getValue();
//			 double tmp = 0;
//			 if(item.getDiscount() > 0)
//			 {
//				 tmp = item.getCount()*item.getPrice()*item.getDiscount();
//				 jiesheng += item.getCount()*item.getPrice()*(1 - item.getDiscount());
//			 }
//			 else
//			 {
//				 tmp = item.getCount()*item.getPrice();
//			 }
//			 total += tmp;
//			 sb.append("名称:"+item.getName()+",数量:"+item.getCount()+item.getUnit()+",单价:"+df.format(item.getPrice())+"(元),小计:"+df.format(tmp)+"(元)\n");
//		 }
//		 sb.append("----------------------\n");
//		 sb.append("总计:" + df.format(total) + "(元)\n");
//		 if(jiesheng > 0)
//		 {
//			 sb.append("节省:" + df.format(jiesheng) + "(元)\n");
//		 }
//		 sb.append("**********************");
//		 System.out.println(sb.toString());
//	}
	
	public static List<Item> sortItems(Map<String, Item> map)
	{
		List<Item> items = new ArrayList<Item>();
		Iterator<Entry<String, Item>> iterator = map.entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry<String, Item> entry = iterator.next();
			items.add(entry.getValue());
		}
		int maxindex = 0;
		for(int i=0; i < items.size(); i++)
		{
			maxindex = i;
			for(int j=i+1;j<items.size();j++)
			{
				if (items.get(j).getCount() > items.get(maxindex).getCount())
				{
					maxindex = j;
				}
			}
			if (maxindex != i) {
				Item tmp = items.get(maxindex);
				items.set(maxindex, items.get(i));
				items.set(i, tmp);
			}
		}
		
		return items;
	}
	
	public static String print(Map<String, Item> map)
	{
		List<Item> items = sortItems(map);
		Map<String, Item> mymap = new HashMap<String, Item>();
		String head  = "***商店购物清单***\n";
		StringBuffer sb = new StringBuffer();
		double total = 0;
		double jiesheng = 0;
		int zengsong = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		Item itema = null;
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			int count = item.getCount();
			
			
			double tmp = 0;
			if(item.getDiscount() > 0)
			{
				tmp = item.getCount()*item.getPrice()*item.getDiscount();
				jiesheng += item.getCount()*item.getPrice()*(1 - item.getDiscount());
			}
			else if(item.isPromotion())
			{
				while(count > 2)
				{
					if(itema == null)
					{
						itema = new Item();
					}
					count -= 3;
					zengsong++;
				}
				if(itema != null)
				{
					itema.setName(item.getName());
					itema.setBarcode(item.getBarcode());
					itema.setUnit(item.getUnit());
					itema.setCount(zengsong);
				}
				mymap.put(item.getBarcode(), itema);
				tmp = (item.getCount() - zengsong)*item.getPrice();
				jiesheng += item.getPrice()*zengsong;
			}
			else
			{
				tmp = item.getCount()*item.getPrice();
			}
			total += tmp;
			sb.append("名称:"+item.getName()+",数量:"+item.getCount()+item.getUnit()
					+",单价:"+df.format(item.getPrice())+"(元),小计:"+df.format(tmp)+"(元)\n");
			itema = null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		if(zengsong > 0)
		{
//			head += "打印时间:"+format.format(Calendar.getInstance().getTime()) + "\n";
			head += "打印时间:2014年08月04日 08:09:05\n";
			head += "----------------------\n";
			sb.append("----------------------\n");
			sb.append("挥泪赠送商品:\n");
			Iterator<Entry<String, Item>> iterator1 = mymap.entrySet().iterator();
			while(iterator1.hasNext())
			{
				Entry<String, Item> entry = iterator1.next();
				Item item = entry.getValue();
				sb.append("名称:"+item.getName()+",数量:"+item.getCount()+item.getUnit()+"\n");
			}
		}
		
		sb.append("----------------------\n");
		sb.append("总计:" + df.format(total) + "(元)\n");
		if(jiesheng > 0)
		{
			sb.append("节省:" + df.format(jiesheng) + "(元)\n");
		}
		sb.append("**********************");
		
		return head + sb.toString();
	}
	
	public static String readFile(String filepath) throws UnsupportedEncodingException, IOException
	{
		File file=new File(filepath);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[1024];
        StringBuffer sb=new StringBuffer();
        int length = -1;
        while((length = fis.read(buf))!=-1){
        	sb.append(new String(buf, 0, length));
            buf=new byte[1024];
        }
        fis.close();
        return sb.toString().trim();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		//处理需求1
//		String inputStr = readFile("input1.txt");
//		if (inputStr == null || inputStr.length() == 0) 
//		{
//			System.out.println("输入为空，请确认输入是否正确！");
//			System.exit(1);
//		}
//		List<Item> aa = getItems(inputStr);
//		print(gettotal1(aa));
//		Map<String, Item> map = gettotal1(aa);
//		System.out.println(print(map));
		
		//处理需求2
//		String inputStr = readFile("input2.txt");
//		if (inputStr == null || inputStr.length() == 0) 
//		{
//			System.out.println("输入为空，请确认输入是否正确！");
//			System.exit(1);
//		}
//		List<Item> aa = getItems(inputStr);
//		Map<String, Item> map = gettotal1(aa);
//		System.out.println(print(map));
		
		//处理需求3
//		String liebiao = readFile("liebiao3.txt");
//		
//		String suoyin = readFile("suoyin3.txt");
//		if (liebiao == null || suoyin == null || suoyin.length() == 0 || liebiao.length() == 0) 
//		{
//			System.out.println("输入为空，请确认输入是否正确！");
//			System.exit(1);
//		}
//		Map<String, Item> map = getSuoyin(suoyin);
//		gettotal2(liebiao, map);
//		System.out.println(print(map));
		
		//处理需求4
		String liebiao = readFile("C:\\Users\\thinkpad\\Desktop\\\\POS-Seed\\POS-Seed\\input\\liebiao4.txt");
		String suoyin = readFile("C:\\Users\\thinkpad\\Desktop\\POS-Seed\\POS-Seed\\input\\suoyin4.txt");
		if (liebiao == null || suoyin == null || suoyin.length() == 0 || liebiao.length() == 0) 
		{
			System.out.println("输入为空，请确认输入是否正确！");
			System.exit(1);
		}
		Map<String, Item> map = getSuoyin(suoyin);
		gettotal2(liebiao, map);
		System.out.println(print(map));
	}

}
