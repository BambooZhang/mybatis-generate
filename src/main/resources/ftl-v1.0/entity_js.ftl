
var ${tableProperty.objName}= {
	listVm:'',//view dom
	param:{pageSize:10,pageNo:1},
	premissionList:['view','detail','add','update','del','pageList'],//JSON.parse(localStorage.getItem(App.currentUrl)),//当前页面所拥有的权限[view,detail,add,update,del,pageList]
	basePath:xialeme.config.root+"/${sysConfig.moduleName}/${tableProperty.objName}",
    init: function () {
    	
    	/*$('#${tableProperty.objName} .summernote').summernote({
    		minHeight: 300
   		});*/
    	//初始化vew
    	this.vueInit();
    	//初始化权限按钮
    	this.premissionInit();
		//列表页面分页方法调用
    	this.pageList();
        
     
    },
    premissionRefresh:function () {//分页刷新操作权限
    	this.premissionList.forEach(function(val,index,arr){
    		  $("#${tableProperty.objName} ."+val+"Operate").show(); 
    		  $("#${tableProperty.objName} ."+val+"Button").show();
    	});
    },
    premissionInit:function () {
    	this.premissionRefresh();    	
    	//列表页面搜索调用
		$("#${tableProperty.objName} .searchOperate").click(function(){
			${tableProperty.objName}.param.pageNo=1;//每次搜索的时候都重新从第一页开始，防止遗留跳页
			${tableProperty.objName}.pageList();
        });
		//添加页面显示
		$("#${tableProperty.objName} .addOperate").click(function(){
			//hidden list div & show dataform div 
			${tableProperty.objName}.detail(null,"add");
			
		});
		//form cancel
		$("#${tableProperty.objName} .cancleButton").click(function(){
			${tableProperty.objName}.clearForm();//自动清空表单
			${tableProperty.objName}.showPageList();
		});
		//form add
		$("#${tableProperty.objName} .addButton").click(function(){
			${tableProperty.objName}.edit("post");
		});
		//form update
		$("#${tableProperty.objName} .submitButton").click(function(){
			${tableProperty.objName}.edit("put");
		});
    	
    	
    },
    vueInit:function () {
    	//初始化component
		Vue.component('v-${tableProperty.objName}-tablelist', {
			props: ['data','lens','selected'],
			template: '#${tableProperty.objName}-table-list-tep',
			filters: {//数据过滤
					filterFun: function (key) {  
		                  //把数字转成自定义的实际含义
		              	var str="";
		              	switch (key) {
		              	case 0:
								str="平台";
								break;
		              	case 1:
								str="任务";
								break;
		              	case 2:
								str="完成任务";
								break;
		              	case 3:
								str="提现 ";
								break;
		              	case 4:
								str="公益捐款 ";
								break;

							default:
								break;
							}
		              	
		              	
		                return str;   
					}  
				},
			computed: {
		            pageArry: function () {
		                var left = 1
		                var right = this.data.pageCount
		                var ar = []
		                if (this.data.pageCount >= 11) {
		                    if (this.data.pageNo > 5 && this.data.pageNo < this.data.pageCount - 4) {
		                        left = this.data.pageNo - 5
		                        right = this.data.pageNo + 4
		                    } else {
		                        if (this.data.pageNo <= 5) {
		                            left = 1
		                            right = 10
		                        } else {
		                            right = this.data.pageCount
		                            left = this.data.pageCount - 9
		                        }
		                    }
		                }
		                while (left <= right) {
		                    ar.push(left)
		                    left++
		                }
		                if (ar[0] > 1) {
		                    ar[0] = 1;
		                    ar[1] = -1;
		                }
		                if (ar[ar.length - 1] < this.data.pageCount) {
		                    ar[ar.length - 1] = this.data.pageCount;
		                    ar[ar.length - 2] = 0;
		                }
		                return ar
		            }
		        },
			 methods: {
					pageSizeUpdate:function() {
						if(this.selected){
							${tableProperty.objName}.param.pageSize=this.selected;
							${tableProperty.objName}.pageList();
						}
						
					},
					pageUpdate: function(pageNo) {
							if(pageNo<1 || pageNo > this.data.pageCount)return;
							//调用分页查询方法接口
							${tableProperty.objName}.param.pageNo=pageNo;
							${tableProperty.objName}.pageList();
					}
				}
		});
    	${tableProperty.objName}.listVm = new Vue({
	            el: '#${tableProperty.objName} .dataTables_wrapper',
	            data: {
	             	data:{data:[],hasNext:true,hasPrevious:false,pageCount:1,pageNo:1,pageSize:10,rowCount:0,startOfPage:1},
	             	lens: [10, 50, 100],
	             	selected:10
	            },
	            methods: {
					refresh: function(dataList) {
						this.data =dataList;
					},
					pageUpdate: function(pageNo) {
						alert(pageNo);
					}
				 }
	  	});
    },
    clickEvent:function(obj){
    	 //bidding click event
    	var operate=$(obj).attr("data-operate" );
    	var keyId=$(obj).parent().parent().children("td:first-child").find("input").val();
    	
    	switch(operate){
    	case "viewOperate":
    		${tableProperty.objName}.detail(keyId, "view");
    		break;
    	case "updateOperate":
    		${tableProperty.objName}.detail(keyId, "edit");
    		break;
    	case "deleteOperate":
    		$.xmConfirm('确定要删除此条信息吗',function(){ ${tableProperty.objName}.del(keyId)});
    		//${tableProperty.objName}.del(keyId);
    		break;
    	case "censorOperate":
    		//自己实现获取审核状态和审核意见
    		//TODO
    		var data={"id":keyId,"censorStatus":1};
    		${tableProperty.objName}.censor(data);
    		break;
    	
    		
    	}
    },
    edit: function (ajaxType) {//添加和修改数据;type:post,put
    	
    	var data = $("#${tableProperty.objName} form[name=dataForm]").serializeJson(); 
    	var url =${tableProperty.objName}.basePath;
    	var type ="post";
    	if(ajaxType =="put"){
    		url =${tableProperty.objName}.basePath+"/update";
    	}
    	
    	$.ajax({
             type:type,
             url:url,
             data: data,
             dataType: "json",
             success: function(result){
				if(result.code==1){
                    $.xmToast({type: "success",text: result.msg});
                    $("#${tableProperty.objName} .searchOperate").trigger("click");
				}else{
					App.handleException(result);
				}
    		}
    	});
    	
    }, 
    detail: function (id, type){
    	
    	if(id&&id!=null){
    		$.ajax({
        		url: ${tableProperty.objName}.basePath+"/"+id,
        		type:"get",
        		success: function(result){
        			if(result.code==1){
    			        ${tableProperty.objName}.loadData(result.data);
    			        var proTep=result.data;
    			        if(type == "view"){//edit
    			        	
    			         	var data={};
    			         	//如果涉及到多级操作需要关联数据时的处理模式demo
    			         	/*
    			         	data.proId=proTep.id;
    			        	data.orgId=proTep.orgId;
    			        	projectReport.loadData(data);
    			        	
    			        	//查询列表的表单赋值
    			        	$("#projectReport form[name=searchform]").find("input[name=proId]").val(proTep.id);
    			         	projectReport.pageList();
    			         	$("#projectReport").show(); */
    			         }
    				}else{
    					App.handleException(result);
    				}
        		}
        	});
    	}
		
    	
		if(type == "view"){
    		var dataFormTitle =$("#${tableProperty.objName} .dataFormTitle").text();
    		var dataFormTitle="查看"+dataFormTitle.substring(2); 
         	$("#${tableProperty.objName} .dataFormTitle").text(dataFormTitle);
         	//只读模式打开
         	$("#${tableProperty.objName} form[name=dataForm]").find("input,textarea,radio,checkbox").attr('readonly',true);
         	$("#${tableProperty.objName} form[name=dataForm]").find("input,textarea").attr('disabled',true);
         	//$("#${tableProperty.objName} .summernote').summernote('destroy');
         	
    	}else if(type == "edit"){//edit
         	var dataFormTitle =$("#${tableProperty.objName} .dataFormTitle").text();
     		var dataFormTitle="修改"+dataFormTitle.substring(2); 
         	$("#${tableProperty.objName} .dataFormTitle").text(dataFormTitle);
         	
         	$("#${tableProperty.objName} .addButton").hide();
         	$("#${tableProperty.objName} .submitButton").show();
         }else{
         	var dataFormTitle =$("#${tableProperty.objName} .dataFormTitle").text();
         	var dataFormTitle="添加"+dataFormTitle.substring(2); 
         	$("#${tableProperty.objName} .dataFormTitle").text(dataFormTitle);
         	
         	$("#${tableProperty.objName} .addButton").show();
			$("#${tableProperty.objName} .submitButton").hide();
         }
    	${tableProperty.objName}.showDataForm();//显示表单
    	
    	
    },
    loadData :function(jsonStr){
        var obj = eval(jsonStr);
        var key,value,tagName,type,arr;
        var form;
        form = $("#${tableProperty.objName} form[name=dataForm]");
        for(x in obj){
            key = x;
            value = obj[x];
            form.find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
                tagName = $(this)[0].tagName;
                type = $(this).attr("type");
                if(tagName=="INPUT"){
                    if(type=="radio"){
                        $(this).prop("checked",$(this).val()==value);
                    }else if(type=="checkbox"){
                        arr = value.split(",");
                        for(var i =0;i<arr.length;i++){
                            if($(this).val()==arr[i]){
                                $(this).prop("checked",true);
                                break;
                            }
                        }
                    }else{
                        $(this).val(value);
                    }
                }else if(tagName=="SELECT" ){
                    $(this).val(value);
                }else if(tagName=="TEXTAREA"){//
                	if($(this).css("display")=="none"){
                		//form.find("textarea[name="+key+"]").summernote("code",value);
                	}else{
                		 $(this).val(value);
                	}
                	
                }
                
                 
            });
            
            
        }
        
    },
    
    //导出Excel
    excel: function () {
    	var data = $("#${tableProperty.objName} form[name=dataForm]").serializeJson(); 
    	$.ajax({
    		url: ${tableProperty.objName}.appPath+"/excel", 
    		data: data,
    		success: function(data){
    			if(data){
    				$.xmToast({type: "success",text: "导出成功"});
    				debugger;
    				window.location.href="/ec-mgt/会员信息.xls";
    			}
    		}
    	});     
    },
    pageList:function () {
    	${tableProperty.objName}.showPageList();//显示分页列表
    	${tableProperty.objName}.clearForm();//自动清空表单
		
    	var pageSize=${tableProperty.objName}.param.pageSize,pageNo=${tableProperty.objName}.param.pageNo;
    	var pageParam="pageSize="+pageSize+"&"+"pageNo="+pageNo;
    	var formdata=$("#${tableProperty.objName} .searchform").serialize();
    	formdata=formdata&&formdata.length>1?(pageParam+"&"+formdata):pageParam;
		$.ajax({
			url: ${tableProperty.objName}.basePath,
			//contentType: "application/json; charset=utf-8",  
			//dataType: "json",
			type:"get",
			data: formdata,
			success: function(result) {
				var listBody =$("#${tableProperty.objName} .listBody");
				if(result.code==1){
                    var data=result;
                    $.xmToast({type: "success",text: result.msg});
                   	${tableProperty.objName}.listVm.refresh(result.data);
                   	
                  //初始化权限按钮
                   	setTimeout(function(){  
                   		${tableProperty.objName}.premissionRefresh();
                   	 },500);  
                   	
				}else{
					App.handleException(result);
				}
				
				
				
			}
		})
    	
    },
    commonUpate:function(url,data){//通用更新数据
    	//data={id:1,censorStatus:1};
    	$.ajax({
			url: url,
			contentType: "application/x-www-form-urlencoded",  
			dataType: "json",
			type:"post",
			data:data,
			success: function(result) {
				if(result.code==1){
                    $.xmToast({type: "success",text: result.msg});
                    project.pageList();
				}else{
					App.handleException(result);
				}
				
			}
		})
    },
    clearForm:function(){//清空表单
    	var form = $("#${tableProperty.objName} form[name=dataForm]");
    	form.clearForm();
    	form.resetForm();
    	form.find("input[type=hidden]").val("");
    	/*form.find("textarea[name=content]").summernote("code","");
    	form.find("textarea[name=content]").summernote("code","");
    	form.find("textarea[name=content]").summernote('destroy');
    	form.find("textarea[name=content]").summernote({
    		minHeight: 300
   		});*/
   		//只读模式关闭
     	$("#${tableProperty.objName} form[name=dataForm]").find('input,textarea,radio,checkbox').attr('readonly',false);
     	$("#${tableProperty.objName} form[name=dataForm]").find('input,textarea').attr('disabled',false);

    },
    showPageList:function(){//显示分页
    	${tableProperty.objName}.clearForm();//自动清空表单
    	
    	$("#${tableProperty.objName} .pageListBody").show();
		$("#${tableProperty.objName} .formOpertBody").hide();
    },
    showDataForm:function(){//显示表单
    	$("#${tableProperty.objName} .pageListBody").hide();
		$("#${tableProperty.objName} .formOpertBody").show();
    },
    del: function (keyId) {
		var data={tepId:keyId,status:1};
    	//${tableProperty.objName}.commonUpate(data);
		$.ajax({
			url: ${tableProperty.objName}.basePath+"/"+keyId,
			contentType: "application/json; charset=utf-8",  
			dataType: "json",
			type:"delete",
			success: function(result) {
				if(result.code==1){
                    $.xmToast({type: "success",text: result.msg});
                    ${tableProperty.objName}.pageList();
				}else{
					App.handleException(result);
				}
			}
		})
    },
    censor:function (data) {
    	//var data={tepId:keyId,censor:censorStatus};
    	var url =project.basePath+"/censor";
    	${tableProperty.objName}.commonUpate(data);
    }

};

