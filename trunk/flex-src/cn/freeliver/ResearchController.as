package cn.freeliver
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	/**
	 * 前端科研控制器
	 * @author freeliver
	 * @version v0.1
	*/
	public class ResearchController {
		private var crtPage:int=1;
		private var datagrid:ArrayCollection=new ArrayCollection();
		private var researchRemoteObject:RemoteObject=null;
		private var application:Object;
		private var module:Object;
		private var totalPage:int=0;
		private var crtOperator:String='thesis';
		
		/**
		*ResearchController() 构造函数
		*/
		
		public function ResearchController(object:Object,module:Object){
			this.application=object;
			this.module=module;
			
			researchRemoteObject=new RemoteObject('cn.freeliver.ResearchService');
			//分页RPC
			researchRemoteObject.getPageList.addEventListener(ResultEvent.RESULT,this.getPageListResult);
			researchRemoteObject.getPageList.addEventListener(FaultEvent.FAULT,this.faultListener);
			
			//添加RPC
			researchRemoteObject.addOne.addEventListener(ResultEvent.RESULT,this.getAddResult);
			researchRemoteObject.addOne.addEventListener(FaultEvent.FAULT,this.faultListener);
			
			//更新RPC
			researchRemoteObject.edit.addEventListener(ResultEvent.RESULT,this.getEditResult);
			researchRemoteObject.edit.addEventListener(FaultEvent.FAULT,this.faultListener);	
			
			//删除RPC
			researchRemoteObject.deleteById.addEventListener(ResultEvent.RESULT,this.getDeleteByIdResult);
			researchRemoteObject.deleteById.addEventListener(FaultEvent.FAULT,this.faultListener);	
			
			//清空RPC
			researchRemoteObject.empty.addEventListener(ResultEvent.RESULT,this.getEmptyResult);
			researchRemoteObject.empty.addEventListener(FaultEvent.FAULT,this.faultListener);									
		}
		
		/**
		 * getPageListResult() 获取分页结果监听器
		 * @return void
		 * @param ResultEvent event
		*/
		
		public function getPageListResult(event:ResultEvent):void{
			var rs:ArrayCollection=event.result as ArrayCollection;
			var meta:Object=rs.removeItemAt(0);		
			this.totalPage=meta['totalPage'];	
			this.module.setView(rs,this.crtPage,meta['totalPage']);
			this.module.createDataGrid();
		}
		
		public function getAddResult(event:ResultEvent):void{
			if(event){
				Alert.show('添加信息成功！','操作提示');
			}else{
				Alert.show('添加信息失败，请重新操作','操作提示');
			}
		}
		public function getEditResult(event:ResultEvent):void{
			if(event){
				Alert.show('修改信息成功！','操作提示');
			}else{
				Alert.show('修改信息失败，请重新操作','操作提示');
			}
		}
		public function getDeleteByIdResult(event:ResultEvent):void{
			if(event){
				Alert.show('删除信息成功！','操作提示');
			}else{
				Alert.show('删除信息失败，请重新操作','操作提示');
			}
		}
		public function getEmptyResult(event:ResultEvent):void{
			if(event){
				Alert.show('清空信息成功！','操作提示');
			}else{
				Alert.show('清空信息失败，请重新操作','操作提示');
			}			
		}
		
		/**
		 * faultListener() 错误监听器
		 * @return void
		 * @param FaultEvent event
		*/
		
		public function faultListener(event:FaultEvent):void{
			Alert.show(event.message.toString());
		}
		
		/**
		*setOperator() 设置操作
		*@return void
		*@param String o 操作名称
		*/
		
		public function setOperator(o:String):void{
			this.crtOperator=o;
		}
		
		/**
		 * firstPage() 首页
		 * @return void
		*/
		
		public function firstPage():void{
			this.crtPage=1;
			this.getPageList(this.crtPage);
			
		}
		
		/**
		 * nextPage() 下一页
		 * @return void
		*/
		
		public function nextPage():void{
			if(this.crtPage<this.totalPage){
				this.crtPage++;
				this.getPageList(this.crtPage);
			}
		}
		
		/**
		 * prevPage() 上一页
		 * @return void
		*/
				
		public function prevPage():void{
			if(this.crtPage>1){
				this.crtPage--;
				this.getPageList(this.crtPage);
			}
			
		}
		
		/**
		 * lastPage() 最后一页
		 * @return void
		*/
				
		public function lastPage():void{
			this.crtPage=this.totalPage;
			this.getPageList(this.crtPage);
		}		
		
		/**
		 * getPageList() 获取分页
		 * @return void
		*/
				
		public function getPageList(page:int):void{
			
			researchRemoteObject.getPageList(page,this.crtOperator);
		}
		public function add(data:ArrayCollection,o:String):void{
			researchRemoteObject.addOne(data,o);
		}
		public function deleteById(id:int,o:String):void{
			researchRemoteObject.deleteById(id,o);
		}
		public function edit(data:ArrayCollection,o:String):void{
			researchRemoteObject.edit(data,o);
		}
		public function empty(o:String):void{
			Alert.cancelLabel="取消操作";
			Alert.yesLabel="确认操作";
			var i:Alert=Alert.show('您确定要清除所有数据？此操作不可逆！','清空提示');
			if(i.buttonMode==Alert.CANCEL){
				return;
			}else{
				researchRemoteObject.empty(o);
			}
		}

	}
}