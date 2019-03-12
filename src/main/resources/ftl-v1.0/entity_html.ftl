<!DOCTYPE html>

<head>
<meta charset="utf-8" />
<title>【${tableProperty.objName}自动生成代码】管理</title>

</head>
<body  >

<div id="${tableProperty.objName}" ><!-- WRAP START -->



	<!--面包屑导航-->
	 
	<!--列表START-->
	<div class="portlet-body pageListBody">


		<!-- list_div start -->
            <!-- /.box-header -->
            <div class="box-body box box-info">
		            <div class="box-header with-border">
		            	<!-- 操作栏 SATART-->
						<div class="clearfix form">
							<form  name="searchform"  class="form-horizontal searchform">
									<div class="input-group col-md-3 pull-left">
						                <span class="input-group-addon">标题</span>
						                <input type="text" class="form-control small" name="title" placeholder=" 标题">
						             </div>
				              
				
									<div class="input-group col-md-3 pull-left">
										<label class="control-label input-group-addon">栏目名称:</label>
										<select class="small form-control" tabindex="1">
											<option value="XM">下了么</option>
											<option value="JZM">兼职喵</option>
											<option value="ZSB">掌上宝</option>
										</select>
									</div>
				
									<div class="input-group col-md-3 pull-left">
											<label class="input-group-addon">日期:</label>
													<input size="16" type="text" value="" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd 0:0:0'})">
											<div class="input-group-addon"> <i class="fa fa-calendar"></i> </div>
									</div>
				
									
							</form>
				
								
							<div class="controls pull-left">
								<button type="button" class="btn btn-default bg-green searchOperate">
									<i class="icon-search"></i>查询
								</button>
							</div>	
							<div class="btn-group pull-right">
				              <button type="button" class="btn btn-warning  addOperate">Add New</button>
				              <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown">
				                <span class="caret"></span>
				                <span class="sr-only">Toggle Dropdown</span>
				              </button>
				              <ul class="dropdown-menu" role="menu">
				                <li><a href="#">Print</a></li>
								<li><a href="#">Save as PDF</a></li>
								<li><a href="#">Export to Excel</a></li>
				                <li class="divider"></li>
				                <li><a href="#">Separated link</a></li>
				              </ul>
				            </div>
				            
				            
				
						</div>
						<!-- 操作栏 END-->
		            </div>
		            
		            
            	<div class="dataTables_wrapper form-inline dt-bootstrap">
	              
	              	<!-- table-list compoent  START -->
	              		<v-${tableProperty.objName}-tablelist v-bind:data="data"  v-bind:lens="lens" v-bind:lens="selected"></v-${tableProperty.objName}-tablelist>
	              	<!-- table-list compoent  END -->
              
              
            	</div>
        	</div> 
    	<!-- list_div end -->


	</div><!--列表END-->




	<!-- form_div Start -->
	<div class="box box-info formOpertBody" style="display:none">
           <div class="box-header with-border">
             <h3 class="box-title dataFormTitle">添加【${tableProperty.objName}自动生成代码】内容</h3>
           </div>
           <!-- /.box-header -->
           <!-- form start -->
           <form class="form-horizontal"  name="dataForm" >
             <div class="box-body">
             	
             	<input type="hidden" class="small form-control" name="${primaryKey}">
				<!-- 自动生成表单字段START -->
				<#list columList as columnProperty >
				<div class="form-group col-md-12">
					<label class="control-label col-md-3">${columnProperty.colComment}:</label>
					<div class="controls col-md-3">
						<input type="text" class="form-control" name="${columnProperty.javaName}" placeholder="${columnProperty.colComment}">
					</div>
				</div>
				</#list>
				<!-- 自动生成表单字段END -->
				
				
				
				<!-- 辅助参考表单字段,如用可手动删除 -->
             <!--
             	<div class="form-group col-md-12">
				<label class="control-label col-md-3">栏目:</label>
					<div class="controls col-md-6">
						<select class="small form-control" tabindex="1"  name="catId">
							<option value="0">文章</option>
							<option value="1">公司新闻</option>
							<option value="2">技术热点</option>
						</select>
					</div>
				</div>
				<div class="form-group col-md-12">
					<label class="control-label col-md-3">是否显示:</label>
					<div class="controls col-md-6">
						<label class="radio-inline">
							<span><input type="radio" name="isShow" value="1" checked="true">是
						</label>
						<label class="radio-inline">
							<input type="radio" name="isShow" value="0" >否
						</label>
					</div>
				</div> -->
			
			
			 
             
             </div> <!-- /.box-body -->
             <div class="box-footer">
               <div class="text-center">
				<button type="button" class="btn btn-default cancleButton"><i class="fa fa-reply-all"></i>取消</button>
				<button type="button" class="btn btn-default bg-green addButton">
					<i class="fa fa-check"></i> 添加
				</button>
				<button type="button" class="btn btn-default bg-blue submitButton">
					<i class="fa fa-save"></i> 提交
				</button>

			</div>
             </div>
             <!-- /.box-footer -->
           </form>
       </div><!-- form_div End -->

		
		
		
		<!-- VUE template SATART -->
		<template id="${tableProperty.objName}-table-list-tep" style="display:none">
			<div class="row">
			<div class="row">
          		<div class="col-sm-12">
              		<table  class="table table-bordered table-hover dataTable" role="grid" aria-describedby="example2_info">
	                <thead>
		                <tr role="row">
		                	<th style="width: 8px;"><input type="checkbox"
							class="group-checkable" data-set="#sample_1 .checkboxes" /></th>
							<!-- 自动生成列表标头START -->
						<#list columList as columnProperty >
						<#if columnProperty_index lte 3 >
							<th>${columnProperty.colComment}</th>
						<#else>
							<th  class="hidden-480">${columnProperty.colComment}</th>
						</#if>
						</#list>
							<!-- 自动生成列表标头END -->
							<th>操作</th>
		                </tr>
	                </thead>
	                <tbody class="listBody">
						<tr class="odd gradeX" v-for="item in data.data">
							<td :data-id="item.${primaryKey}"><input type="checkbox" class="checkboxes" :value="item.${primaryKey}" /></td>
							<!-- 自动生成列表VUE模板标签START -->
							<#list columList as columnProperty >
							<#if columnProperty_index lte 3 >
								<td>{{item.${columnProperty.javaName}}}</td>
							<#else>
								<td  class="hidden-480">{{ item.${columnProperty.javaName}}}</td>
							</#if>
							</#list>
							<!-- 自动生成列表VUE模板标签END -->
							<td>
								<span class="label bg-blue pointer updateOperate" data-operate="updateOperate" onclick="${tableProperty.objName}.clickEvent(this)"><i class="icon-edit"></i> 修改</span>
								<span class="label bg-black pointer deleteOperate"  data-operate="deleteOperate" onclick="${tableProperty.objName}.clickEvent(this)" v-if=" item.isDeleted < 1 "><i class="icon-trash"></i> 删除</span>
							</td>
						</tr>
	                </tbody>
                
             		</table>
          		</div>
          	</div>
          	
          	<div class="row">
          		<div class="form-group col-sm-6">
          			<div class="dataTables_info col-md-4" role="status" aria-live="polite">当前第{{data.pageNo}}页/共{{data.rowCount}}条记录</div>
					<div class="form-group col-md-2">
						<select class="small form-control" v-model="selected"  @change="pageSizeUpdate">
					        <option v-for="(option, index) in lens" :value="option"  :selected="index === 0 ? true : false">{{option}}</option>
					    </select>
					</div>
	          	</div>
	          	<div class="col-sm-6">
	          		<div class="dataTables_paginate paging_simple_numbers  pull-right">
	          			<ul class="pagination">
	          				<li class="paginate_button previous"  v-bind:class="{disabled:!data.hasPrevious}" v-on:click="data.hasNext | pageUpdate(data.pageNo-1)"  ><a href="#" aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a></li>
	          				<li class="paginate_button " v-for="n in pageArry" v-bind:class="{active:data.pageNo==n?true:false}"  v-on:click="pageUpdate(n)">
	          					<a href="#" aria-controls="example2" :data-dt-idx="n" tabindex="0">{{ n < 1 ? "..." : n }}</a>
	          				</li>
	          				<li class="paginate_button next" v-bind:class="{disabled:!data.hasNext}" v-on:click="pageUpdate(data.pageNo+1)"><a href="#" aria-controls="example2" data-dt-idx="7" tabindex="0">Next</a></li>
	          			</ul>
	          		</div>
	          	</div>
	        </div>
          	
          </div>
		</template>
		<!-- VUE template END -->
		
		
		

</div><!-- WRAP END -->



</body>




<!--  <script type="text/javascript" src="http://localhost:8080/xm-web-sys/static/js/jquery-2.1.4.min.js"></script>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<link rel="stylesheet" href="http://localhost:8080/xm-web-sys/static/adm-xm-1.0/dist/css/style.css">



<script type="text/javascript" src="http://localhost:8080/xm-web-sys/static/js/jquery.form.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/xm-web-sys/static/js/common.js"></script>

<script type="text/javascript" src="http://localhost:8080/xm-web-sys/static/js/vue.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/xm-web-sys/static/js/app.js"></script>
<script type="text/javascript" src="http://localhost:8080/xm-web-sys/static/js/config.js"></script>
<script type="text/javascript"	src="http://localhost:8080/xm-web-sys/static/js/models/cms/${tableProperty.objName}.js"></script>

<script src="http://localhost:8080/xm-web-sys/static/js/models/${sysConfig.moduleName}/${tableProperty.objName}.js"></script>  -->
  

  
<script src="static/js/models/${sysConfig.moduleName}/${tableProperty.objName}.js"></script>

  
</script>



<script>

    $(function(){
    
      //App.groupCheckableInit();
      ${tableProperty.objName}.init();
     
       


    });
    
    
    

    
    
    
</script>










    
</html>