package cn.freeliver
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.modules.Module;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class UserController
	{
		private var userRemoteObject:RemoteObject=null;
		private var application:Object;
		private var module:Object;
		private var currentPage:int=1;
		private var totalPage:int=0;
		public function UserController(app:Object,module:Object)
		{
			this.application=app as Application;
			this.module=module as Module;
			userRemoteObject=new RemoteObject("cn.freeliver.UserService");
			userRemoteObject.register.addEventListener(ResultEvent.RESULT,this.getRegisterResult);
			userRemoteObject.register.addEventListener(FaultEvent.FAULT,this.faultListener);
			userRemoteObject.getUserList.addEventListener(ResultEvent.RESULT,this.getUserListIntoContainer);
			userRemoteObject.getUserList.addEventListener(FaultEvent.FAULT,this.faultListener);
			userRemoteObject.changeType.addEventListener(ResultEvent.RESULT,this.getChangeTypeResult);
			userRemoteObject.changeType.addEventListener(FaultEvent.FAULT,this.faultListener);
			userRemoteObject.empty.addEventListener(ResultEvent.RESULT,this.getEmptyResult);
			userRemoteObject.empty.addEventListener(FaultEvent.FAULT,this.faultListener);
			
		}
		public function firstPage():void{
			this.currentPage=1;
			this.getUserListByPage(1);
		}
		public function nextPage():void{
			if(this.currentPage<this.totalPage){
				this.currentPage++;
				this.getUserListByPage(this.currentPage);
			}
		}
		public function prevPage():void{
			if(this.currentPage>1){
				this.currentPage--;
				this.getUserListByPage(this.currentPage);
			}
			
		}
		public function lastPage():void{
			this.currentPage=this.totalPage;
			this.getUserListByPage(this.currentPage);
		}
		public function getUserListIntoContainer(event:ResultEvent):void{
			var rs:ArrayCollection=event.result as ArrayCollection;		
			var meta:Array=rs.removeItemAt(0) as Array;
			this.totalPage=meta[2];
			this.module.setItems(rs,meta[0],meta[1],meta[2],this.currentPage);
		}
		public function getChangeTypeResult(event:ResultEvent):void{
			//TODO 其他处理
		}
		public function getEmptyResult(event:ResultEvent):void{
			if(event.result){
				Alert.show("清空成功","操作提示");
			}else{
				
				Alert.show("清空失败","操作提示");
			}
		}
		public function refresh():void{
			getUserListByPage(this.currentPage);
		}
		public function getRegisterResult(event:ResultEvent):void{
			this.module.showInfo.text=event.result.toString();
		}
		public function faultListener(event:FaultEvent):void{
			Alert.show(event.message.toString());
		}
		public function getUserListByPage(currentPage:int):void{
			userRemoteObject.getUserList(currentPage);
		}
		public function register(userName:String,password:String,email:String,isLock:int):void{
			userRemoteObject.register(userName,password,email,isLock);
		}
		public function changeType(userID:int,type:int):void{
			userRemoteObject.changeType(userID,type);
		}
		public function empty():void{
			userRemoteObject.empty();
		}

	}
}