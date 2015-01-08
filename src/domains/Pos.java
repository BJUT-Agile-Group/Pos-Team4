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

	public static List<Item> getItems(String inputStr)                                       //得到商品列表
	{
		JSONArray json = JSONArray.fromObject(inputStr);
		List<Item> persons = (List<Item>)JSONArray.toList(json, Item.class);
		
        return persons;
	}
	
	public static Map<String, Item> getSuoyin(String inputStr)               //得到索引文件
	{
		JSONObject json = JSONObject.fromObject(inputStr);
		//JSONArray json = input.getJSONArray("item");
		
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

			if(obj.containsKey("vipDiscount") )
			{
				item.setVipDiscount(obj.getDouble("vipDiscount") );
			}
			map.put(key, item);
		}
		return map;
	}

	public static Map<String,User> user(String inputStr)
	{
		JSONObject json = JSONObject.fromObject(inputStr);
		Map<String,User> map= new HashMap<String,User>();

		Iterator<String> keys = json.keys() ;

		while(keys.hasNext() )
		{
			String key = (String)keys.next() ;
			JSONObject obj = (JSONObject)json .get(key) ;
			User user = new User() ;
			user.setId(key);
			user.setVip(obj.getBoolean("isVip"));
			user.setScore(obj.getInt("score") );

			map.put(key,user);
		}
		return map;
	}

	public static void gettotal2(String inputStr, Map<String, Item> suoyin)                        //得到列表文件后通过索引修改商品的数量
	{
		//JSONArray json = JSONArray.fromObject(inputStr);

		JSONObject input =JSONObject.fromObject(inputStr) ;
		JSONArray json = input.getJSONArray("item");
		//JSONArray user = input.getJSONArray("user");

		List<String> persons = (List<String>)JSONArray.toList(json, String.class);
		for (String key : persons)                                                                  //对商品清单进行再加工
		{
			Item item = suoyin.get(key);
			item.setCount(item.getCount() + 1);
		}

	}
	
	public static HashMap gettotal1(List<Item> list)                                         //只通过输入文件得到最终商品清单
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

	public static List<Item> sortItems(Map<String, Item> map)                                       //按照商品数目排序
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
			//////////
			//if(items.get(i).getCount() == 0 )
		//	{
			//	map.remove(items.get(i).getBarcode());
			//}
		}
		
		return items;
	}

	public static User getUser(String inputStr,Map<String ,User> map)
	{
		JSONObject input = JSONObject.fromObject(inputStr) ;
		String json = input.getString("user");
		User  user = new User();

		//List<String> username = (List<String>)JSONArray.toList(json,String.class);
		//for(String key:username)
		//{
			user = map.get(json);
		//}
		return user;
	}
	
	public static String print(Map<String, Item> map,User user)
	{
		List<Item> items = sortItems(map);
		Map<String, Item> mymap = new HashMap<String, Item>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

		String head  = "***商店购物清单***\n";
		head +="会员编号："+user.getId();
		//head +="   会员积分："+user.getScore()+"\n";
		//head +="----------------------\n";
		//head += "打印时间:"+format.format(Calendar.getInstance().getTime()) + "\n";

	//	head += "----------------------\n";
		StringBuffer  sb = new StringBuffer();

		double total = 0;
		double jiesheng = 0;
		int zengsong = 0;

		DecimalFormat df = new DecimalFormat("0.00");

		Item itema = null;

		for (int i = 0; i < items.size(); i++)
		{
			Item item = items.get(i);
			int count = item.getCount();
			
			
			double tmp = 0;

			if(user.getVip())
			{
				if(item.getDiscount()>0)
				{
					tmp = item.getCount() *item.getPrice() *item.getDiscount() *item.getvipDiscount();
					jiesheng += (item .getCount() *item.getPrice() )-tmp;
				}
				else if(item.isPromotion())
				{
					while(count >= 2)
					{
						if(itema == null)
						{
							itema = new Item();
						}
						count -= 1;
						zengsong=1;
					}
					if(itema != null)
					{
						itema =item;
						itema.setCount(item .getCount()+zengsong);

					}
					mymap.put(item.getBarcode(), itema);
					tmp = (item.getCount() - zengsong)*item.getPrice();
					//tmp = item.getCount()*item.getPrice();
					jiesheng += item.getPrice()*zengsong;
				}
				else
				{
					tmp = item.getCount()*item.getPrice()*item.getvipDiscount();
					jiesheng += (item .getCount() *item.getPrice() )-tmp;
				}
			}
			else if(user.getVip() == false)
			{
				if(item.getDiscount() > 0)
				{
					tmp = item.getCount()*item.getPrice()*item.getDiscount();
					jiesheng += item.getCount()*item.getPrice()*(1 - item.getDiscount());
				}
				else if(item.isPromotion())
				{
					while(count >= 2)
					{
						if(itema == null)
						{
							itema = new Item();
						}
						count -= 1;
						zengsong=1;
					}
					if(itema != null)
					{
						itema =item;
						//itema.setName(item.getName());
						//itema.setBarcode(item.getBarcode());
						//itema.setUnit(item.getUnit());
						itema.setCount(item .getCount()+zengsong);
						//itema.setCount(count+zengsong);
					}
					mymap.put(item.getBarcode(), itema);
					tmp = (item.getCount() - zengsong)*item.getPrice();
					//tmp = item.getCount()*item.getPrice();
					jiesheng += item.getPrice()*zengsong;
				}
				else
				{
					tmp = item.getCount()*item.getPrice();
				}
			}

			total += tmp;
			sb.append("名称:"+item.getName()+",数量:"+item.getCount()+item.getUnit()
					+",单价:"+df.format(item.getPrice())+"(元),小计:"+df.format(tmp)+"(元)\n");
			itema = null;
		}
		//SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		if(zengsong > 0)
		{
			//head += "打印时间:"+format.format(Calendar.getInstance().getTime()) + "\n";
			//head += "打印时间:2014年08月04日 08:09:05\n";
			//head += "----------------------\n";
			sb.append("----------------------\n");
			sb.append("挥泪赠送商品:\n");
			Iterator<Entry<String, Item>> iterator1 = mymap.entrySet().iterator();
			while(iterator1.hasNext())
			{
				Entry<String, Item> entry = iterator1.next();
				Item item = entry.getValue();
				sb.append("名称:"+item.getName()+",数量:"+1+item.getUnit()+"\n");
			}
		}
		/*****************************************修改会员积分**************************************************************/
		int a=0;
		if(user.getScore()<=200 && user.getScore()>0)
		{
			a = ((int)total)/5;
			user.setScore(a + user.getScore());
		}
		if(user.getScore() <= 500 && user.getScore() > 200)
		{
			a = ((int)total)/5;
			user.setScore(a*3 + user.getScore()) ;
		}
		if(user.getScore() > 500)
		{
			a = ((int)total)/5;
			user.setScore(user.getScore() + 5*a);
		}

		head +="   会员积分："+user.getScore()+"\n";
		head +="----------------------\n";
		head += "打印时间:"+format.format(Calendar.getInstance().getTime()) + "\n";

		head += "----------------------\n";
		/***************************************************************************************************************/
		sb.append("----------------------\n");
		sb.append("总计:" + df.format(total) + "(元)\n");
		if(jiesheng > 0)
		{
			sb.append("节省:" + df.format(jiesheng) + "(元)\n");
		}
		sb.append("**********************");
		
		return head + sb.toString();
	}

	public static String print(Map<String, Item> map)
	{
		List<Item> items = sortItems(map);
		Map<String, Item> mymap = new HashMap<String, Item>();

		String head = "***商店购物清单***\n";

		StringBuffer sb = new StringBuffer();

		double total = 0;
		double save = 0;
		int zengsong = 0;

		DecimalFormat df = new DecimalFormat("0.00");							 //输出数字格式化为0.00

		Item itema = null;
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			int count = item.getCount();


			double tmp = 0;
			if(item.getDiscount() > 0)
			{
				tmp = item.getCount()*item.getPrice()*item.getDiscount();
				save += item.getCount()*item.getPrice()*(1 - item.getDiscount());
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
					itema = item;
					itema.setCount(item.getCount() +zengsong);
				}
				mymap.put(item.getBarcode(), itema);
				tmp = (item.getCount() - zengsong)*item.getPrice();
				save += item.getPrice()*zengsong;
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
		head += "打印时间:"+format.format(Calendar.getInstance().getTime()) + "\n";
//				head += "打印时间:2014年08月04日 08:09:05\n";
		head += "----------------------\n";
		if(zengsong > 0)
		{

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
		if(save > 0)
		{
			sb.append("节省:" + df.format(save) + "(元)\n");
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
}
