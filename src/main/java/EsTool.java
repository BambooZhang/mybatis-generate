import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.squareup.okhttp.internal.http.Transport;


/*********
 * 
 * @author xialeme
 * 
 * api使用介绍
 * http://blog.csdn.net/molong1208/article/details/50512149
 *
 */

public class EsTool {  
    public static String index ="employee";  
    
    
    public static TransportClient getClient(){
        try {
            //设置集群名称  
                Settings settings = Settings.builder().put("cluster.name", "my-application").build();  
                //创建client  
                @SuppressWarnings({ "resource", "unchecked" })  
                TransportClient client = new PreBuiltTransportClient(settings)  
                        .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.91"), 9300));  
                return client;  
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
            return null;  
        }
    }  
      
    /** 
     * query通用查询
     * 根据文档名、字段名、字段值查询某一条记录的详细信息；query查询 
     * @param type  文档名，相当于oracle中的表名，例如：ql_xz； 
     * @param key 字段名，例如：bdcqzh 
     * @param value  字段值，如：“” 
     * @return  List 
     * @author Lixin 
     */  
    public static List query(String type,String key,String value){  
    	TransportClient client = getClient();  
    	MatchPhraseQueryBuilder qb = QueryBuilders.matchPhraseQuery(key, value);  
    	
    	
    	RangeQueryBuilder ap= QueryBuilders.rangeQuery("age").gt(10).lt(20);
    	
    	 BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();  
        /* for (String in : map.keySet()) {  
                         //map.keySet()返回的是所有key的值  
                          String str = map.get(in);//得到每个key多对用value的值  
                          boolQueryBuilder.must(QueryBuilders.wildcardQuery(in,str));  
                     }  */
    	
    	SearchResponse response = client.prepareSearch(index).setTypes(type)
    			.setQuery(qb)//QueryBuilders.matchAllQuery())//QueryBuilders.matchQuery("_id", "1"))    
    			.setFrom(0).setSize(10000).setExplain(true)    
    			.execute()
    			.actionGet();  
    	return responseToList(client,response);  
    }  
    
    
    
    
    /** 
     * query基本查询match
     * 根据文档名、字段名、字段值查询某一条记录的详细信息；query查询 
     * @param type  文档名，相当于oracle中的表名，例如：ql_xz； 
     * @param key 字段名，例如：bdcqzh 
     * @param value  字段值，如：“” 
     * @return  List 
     * @author Lixin 
     */  
    public static List getQueryDataBySingleField(String type,String key,String value){  
        TransportClient client = getClient();  
        MatchPhraseQueryBuilder qb = QueryBuilders.matchPhraseQuery(key, value);  
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                        .setQuery(qb)//QueryBuilders.matchAllQuery())//QueryBuilders.matchQuery("_id", "1"))    
                        .setFrom(0).setSize(10000).setExplain(true)    
                        .execute()
                        .actionGet();  
        return responseToList(client,response);  
    }  
      
      
    /** 
     * 多条件query查询
     * 多条件  文档名、字段名、字段值，查询某一条记录的详细信息 
     * @param type 文档名，相当于oracle中的表名，例如：ql_xz 
     * @param map 字段名：字段值 的map 
     * @return List 
     * @author Lixin 
     */  
    public static List getBoolDataByMuchField(String type,Map<String,String> map){  
        TransportClient client = getClient();  
         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();  
         for (String in : map.keySet()) {  
             //map.keySet()返回的是所有key的值  
              String str = map.get(in);//得到每个key多对用value的值  
              boolQueryBuilder.must(QueryBuilders.termQuery(in,str));  
         }  
         SearchResponse response = client.prepareSearch(index).setTypes(type)  
                    .setQuery(boolQueryBuilder)
                    .setFrom(0).setSize(10000).setExplain(true)       
                    .execute()       
                    .actionGet();  
         return responseToList(client,response);
    }  
      
      
    /** 
     * 单条件 模糊查询 
     * @param type 文档名，相当于oracle中的表名，例如：ql_xz 
     * @param key 字段名，例如：bdcqzh 
     * @param value 字段名模糊值：如 *123* ;?123*;?123?;*123?; 
     * @return List 
     * @author Lixin 
     */  
    public List getDataByillegible(String type,String key,String value){  
        TransportClient client = getClient();  
        WildcardQueryBuilder wBuilder = QueryBuilders.wildcardQuery(key, value);  
        SearchResponse response = client.prepareSearch(index).setTypes(type)  
                .setQuery(wBuilder)    
                .setFrom(0).setSize(10000).setExplain(true)    
                .execute()    
                .actionGet();  
        return responseToList(client,response);  
    }  
      
    /** 
     * 多条件 模糊查询 
     * @param type  type 文档名，相当于oracle中的表名，例如：ql_xz 
     * @param map   包含key:value 模糊值键值对 
     * @return List 
     * @author Lixin 
     */  
    public List getDataByMuchillegible(String type,Map<String,String> map){
        TransportClient client = getClient();  
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();  
         for (String in : map.keySet()) {  
                         //map.keySet()返回的是所有key的值  
                          String str = map.get(in);//得到每个key多对用value的值  
                          boolQueryBuilder.must(QueryBuilders.wildcardQuery(in,str));  
                     }  
         SearchResponse response = client.prepareSearch(index).setTypes(type)  
                    .setQuery(boolQueryBuilder)    
                    .setFrom(0).setSize(10000).setExplain(true)    
                    .execute()    
                    .actionGet();  
           
         return responseToList(client,response);  
    }  
      
      
    /**
     * 将查询后获得的response转成list 
     * @param client 
     * @param response 
     * @return 
     */  
    public static List responseToList(TransportClient client,SearchResponse response){  
    	
    	//System.out.println(response.toString());
        SearchHits hits = response.getHits();  
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
        for (int i = 0; i < hits.getHits().length; i++) {  
            Map<String, Object> map = hits.getAt(i).getSourceAsMap();  
            System.out.println(map.toString());
            list.add(map);  
        }  
        client.close();  
        return list;  
    }
    
    
    
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		List list = getQueryDataBySingleField("employee", "address", "Street");//查询所有"address"含有 "Street"的
		
		
//		Map<String,String> arg=new HashMap<String, String>();
//		arg.put("address", "Street");
//		getBoolDataByMuchField("employee", arg);//查询所有"address"含有 "Street"的
//		
	}
    
    
    
    
    
    
}  