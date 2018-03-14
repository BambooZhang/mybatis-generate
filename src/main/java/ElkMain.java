import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;

/***********
 * 
 * 版本elasticsearch6.0.0
 * Document APIs的使用
 * 
 * @author bamboo
 *
 */
public class ElkMain {

	
	private static TransportClient client;
	
	 public static void getClient() {
		// TODO Auto-generated method stub
		 try {  
		      
		      //设置集群名称  
		  
		      Settings settings = Settings.builder().put("cluster.name", "my-application").build();  
		      //创建client  
		      //@SuppressWarnings("resource")  
		      client = new PreBuiltTransportClient(settings)  
		              .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.91"), 9300));  
		      
		      
		      //写入数据  
		     // createDate(client);  
		      //搜索数据  
//		      GetResponse response = client.prepareGet("accounts", "person", "1").execute().actionGet();  
//		      //输出结果  
//		      System.out.println(response.getSource());  
		        
		      //关闭client  
		      //client.close();  
		  } catch (Exception e) {  
		      e.printStackTrace();  
		  }  
	}
	
	
	
	
	/** 
	   * 插入单条数据 
	   * {"account_number":44,"balance":34487,"firstname":"Aurelia","lastname":"Harding","age":37,"gender":"M","address":"502 Baycliff Terrace","employer":"Orbalix","email":"aureliaharding@orbalix.com","city":"Yardville","state":"DE"}

	   * @param client 
	   */  
   public static void addDate(){
    Map<String,Object> map = new HashMap<String, Object>();  
    map.put("account_number", 47);  
    map.put("balance", 34487);  
    map.put("firstname", "Aurelia");  
    map.put("lastname", "Harding");  
    map.put("age", 37);  
    map.put("gender","M");  
    map.put("address","浙江省杭州市江干区人民公园中国梦");  
    map.put("gender","M");  
    map.put("employer","小李");
    map.put("email","aureliaharding@orbalix.com");
    map.put("city","Yardville");
    map.put("state","DE");  
    
 // instance a json mapper
//    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
//
//    // generate json
//    byte[] json = mapper.writeValueAsBytes(yourbeaninstance);
    
    try {  
    	
    	//json格式
    	 String json=JSON.toJSONString(map);
    	IndexResponse response = client.prepareIndex("accounts", "person","47")
        .setSource(json, XContentType.JSON)
        .get();
    	
//        IndexResponse response = client.prepareIndex("accounts", "person","45")  //UUID.randomUUID().toString()
//                   .setSource(map).execute().actionGet();  
        System.out.println(response.toString());  
        System.out.println("写入数据结果=" + response.status().getStatus() + "！id=" + response.getId());  
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
   } 
	
   
   
   
   //获取单条数据
   public static void getDataByID(String id) {
		// TODO Auto-generated method stub
		 try {  
		      
		      //搜索数据  
		      GetResponse response = client.prepareGet("accounts", "person", id).execute().actionGet();  
		      //输出结果  
		      System.out.println(response.getSource());  
		        
		      //关闭client  
		      //client.close();  
		  } catch (Exception e) {  
		      e.printStackTrace();  
		  }  
	}
   
   /**
    * 删除单条记录 delete api
    * 
    */
   public static void deleteById(String id) {
       DeleteResponse response = client.prepareDelete("accounts", "person", id)
               .get();
       String index = response.getIndex();
       String type = response.getType();
       String rid = response.getId();
       long version = response.getVersion();
       System.out.println(response.toString());  
       System.out.println(index + " : " + type + ": " + rid + ": " + version);
   }
	
   
   
   
   /**
    * 测试更新 update API
    * 使用 updateRequest 对象
    * @throws Exception 
    */
   public static void update(String id)  {
		UpdateRequest updateRequest = new UpdateRequest();
      
      //client.prepareUpdate("accounts", "person", "1")和下面相同
//	       updateRequest.index("accounts");
//	       updateRequest.type("person");
//	       updateRequest.id(id);
//	       updateRequest.doc(XContentFactory.jsonBuilder()
//	               .startObject()
//	               // 对没有的字段添加, 对已有的字段替换
//	               .field("employer", "小李子22")
//	                .field("gender", "M")
//	               .endObject());		
 //UpdateResponse response = client.update(updateRequest).get();
      
      // 使用Script对象进行更新
//    UpdateResponse response = client.prepareUpdate("accounts", "person", id)
//		     .setScript(new Script("ctx._source.employer = \"小李子22\""))
//		     .get();
    
    // 使用XContFactory.jsonBuilder() 进行更新
     UpdateResponse response = null;
		try {
			response = client.prepareUpdate("accounts", "person", id)
			         .setDoc(XContentFactory.jsonBuilder()
			                 .startObject()
			                     .field("employer", "小李子333")
			                 .endObject()).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    // 使用updateRequest对象及script
//     UpdateRequest updateRequest = new UpdateRequest("accounts", "person", id)
//             .script(new Script("ctx._source.gender=\"male\""));
//     UpdateResponse response = client.update(updateRequest).get();
      
      
     
      
      // 打印
      String index = response.getIndex();
      String type = response.getType();
      String rid = response.getId();
      long version = response.getVersion();
      System.out.println(response.toString());  
      System.out.println(index + " : " + type + ": " + rid + ": " + version);
   }
   
   
   /**
   * 测试upsert方法
   * 查询条件, 查找不到则添加生效
   * @throws Exception 
   * 
   */
  public static void upSet(String id) {
      try {
		// 设置查询条件, 查找不到则添加生效
		  IndexRequest indexRequest = new IndexRequest("accounts", "person", id)
		      .source(XContentFactory.jsonBuilder()
		          .startObject()
		              .field("name", "qergef")
		              .field("gender", "malfdsae")
		          .endObject());
		  // 设置更新, 查找到更新下面的设置
		  UpdateRequest upSet = new UpdateRequest("accounts", "person", id)
		      .doc(XContentFactory.jsonBuilder()
		              .startObject()
		                  .field("user", "wenbronk")
		              .endObject())
		      .upsert(indexRequest);
		  
		  client.update(upSet).get();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      
  }
  
   /**
   * 测试multi get api
   * 从不同的index, type, 和id中获取
   */ 
  public static void testMultiGet() {
      MultiGetResponse multiGetResponse = client.prepareMultiGet()
      .add("twitter", "tweet", "1")
      .add("accounts", "person",  "6", "13", "18")//因为我数据里面刚好有这几个ID,根据你自己的情况设置
      .add("index", "type", "foo")
      .get();
      
      for (MultiGetItemResponse itemResponse : multiGetResponse) {
          GetResponse response = itemResponse.getResponse();
          if (response!=null&&response.isExists()) {
              String sourceAsString = response.getSourceAsString();
              System.out.println(sourceAsString);
          }
      }
  }
  
  
  
  
  
  /**
   * bulk 批量执行
   * 一次查询可以update 或 delete多个document
   */
  public static void testBulk()  {
      try {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		  bulkRequest.add(client.prepareIndex("accounts", "person", "50")
		          .setSource(XContentFactory.jsonBuilder()
		                  .startObject()
		                      .field("user", "kimchy")
		                      .field("postDate", new Date())
		                      .field("message", "trying out Elasticsearch")
		                  .endObject()));
		  bulkRequest.add(client.prepareIndex("accounts", "person", "51")
		          .setSource(XContentFactory.jsonBuilder()
		                  .startObject()
		                      .field("user", "kimchy")
		                      .field("postDate", new Date())
		                      .field("message", "another post")
		                  .endObject()));
		  BulkResponse response = bulkRequest.get();
		  System.out.println(response.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
   
  
  
  /**
   * 使用bulk processor
   * @throws Exception 
   */
  public static void testBulkProcessor() throws Exception {
      // 创建BulkPorcessor对象
      BulkProcessor bulkProcessor = BulkProcessor.builder(client, new Listener() {

		@Override
		public void beforeBulk(long executionId, BulkRequest request) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterBulk(long executionId, BulkRequest request,
				BulkResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterBulk(long executionId, BulkRequest request,
				Throwable failure) {
			// TODO Auto-generated method stub
			
		}
         
      })
      // 1w次请求执行一次bulk
      .setBulkActions(10000)
      // 1gb的数据刷新一次bulk
      .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
      // 固定5s必须刷新一次
      .setFlushInterval(TimeValue.timeValueSeconds(5))
      // 并发请求数量, 0不并发, 1并发允许执行
      .setConcurrentRequests(1)
      // 设置退避, 100ms后执行, 最大请求3次
      .setBackoffPolicy(
              BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
      .build();
      
      // 添加单次请求
      bulkProcessor.add(new IndexRequest("accounts", "person", "50"));
      bulkProcessor.add(new DeleteRequest("accounts", "person", "51"));
      
      // 关闭
      bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
      // 或者
      bulkProcessor.close();
  }
   
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		getClient();
		addDate();
		getDataByID("47");
		//deleteById("45");
//		update("44");
//		getDataByID("44");
		
//		upSet("46");//查找设置
//		getDataByID("46");
		
//		testMultiGet();
		
		//testBulk();//批量执行
		//getDataByID("51");
		
		
//		testBulkProcessor();//批量处理,少于1w次不会执行
//		getDataByID("51");
		
	}

}
