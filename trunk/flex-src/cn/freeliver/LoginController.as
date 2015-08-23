package cn.freeliver
{

	import flash.system.System;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.modules.Module;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;


	/**
	 * LoginController 登陆控制器
	 * @author freeliver
	 * @email freeliver<freeliver204@gmail.com>
	 * @package cn.freeliver
	 * @version 0.1
	 * @lastModified 2010/04/11 23:48
	*/
		
	public class LoginController	
	{
		private var userName:String="";
		private var password:String="";
		private var application:Object=null;
		private var loginModule:Object=null;
		private var loginRemoteObject:RemoteObject=null;
		private var loginConfig:Array;
		
		public function LoginController(application:Object,loginModule:Object)
		{

			this.application=application as Application;
			this.loginModule=loginModule as Module;
			//使用在remote-config.xml中注册的远程类
			loginRemoteObject=new RemoteObject("cn.freeliver.loginService");
			loginRemoteObject.login.addEventListener(ResultEvent.RESULT,this.getLoginResultListener);
			loginRemoteObject.login.addEventListener(FaultEvent.FAULT,this.faultListener);
			
			loginRemoteObject.getLoginConfig.addEventListener(ResultEvent.RESULT,this.getLoginConfigListener);
			loginRemoteObject.getLoginConfig.addEventListener(FaultEvent.FAULT,this.faultListener);
			
			loginRemoteObject.register.addEventListener(ResultEvent.RESULT,this.getRegisterListener);
			loginRemoteObject.register.addEventListener(FaultEvent.FAULT,this.faultListener);

		}
		
		private function getLoginConfigListener(event:ResultEvent):void{
			this.loginConfig=event.result as Array;
			if(int(loginConfig[0])==1){
				loginModule.registerButton.enabled=true;
			}else{
				loginModule.registerButton.enabled=false;	
			}
		}
		public function configLoginPanel():void{
			loginRemoteObject.getLoginConfig();
		}
		
		public function getLoginConfig():Array{
			return this.loginConfig;
		}
		

		
		/**
		 * getLoginResultListener 登陆结果监听器
		 * @param ResultEvent event
		 * @return void
		 * @access public
		*/
				
		public function getLoginResultListener(event:ResultEvent):void{

			loginRemoteObject.showBusyCursor=false;
			var loginInfo:Array=event.result as Array;
			var loginStatus:int=int(loginInfo[0]);
			
			//Alert.show(event.result.toString());
			//return void;
			
			switch(loginStatus){
				case 0:
				Alert.show(loginInfo[1],"登陆错误");
				this.application.loginModule.password.text="";
				break;
				case 1:
				Alert.show(loginInfo[1],"登陆错误");
				this.application.loginModule.password.text="";
				break;
				case 2:
				//转到管理主视图，注意这里必须在以下的基本设置之前，因为视图转换才能导致一些视图的元件初始化，才能被引用到
				this.application.currentState="main";

				//登陆成功后设置一些基本参数
				this.application.accessList.setAccessList(loginInfo[3],'-||-');
				this.application.userRole=loginInfo[4];
				this.application.userInformation.htmlText="欢迎您："+this.userName+" 角色：<font color=\"#FF0000\">"+loginInfo[4]+"</font> 当前时间："+loginInfo[8];
				this.application.homePageModule.welcome_text.htmlText="欢迎："+this.userName+" 您所属的角色：<font color=\"#FF0000\">"+loginInfo[4]+"</font> <br/>上次登陆时间："+loginInfo[7];
				this.application.homePageModule.welcome_text.htmlText+="<br/>上次登陆的IP地址："+loginInfo[6];
				this.application.homePageModule.welcome_text.htmlText+="<br/>您已经使用本系统："+loginInfo[5]+" 次";
				
				this.application.homePageModule.runtime_text.htmlText="当前VM版本："+System.vmVersion;
				this.application.homePageModule.runtime_text.htmlText+=" 使用内存："+System.totalMemory/1024+" kb";
				this.application.homePageModule.runtime_text.htmlText+="<br/>";		
				this.application.homePageModule.runtime_text.htmlText+="当前浏览器版本：";
				this.application.homePageModule.runtime_text.htmlText+="<br/>";					
				this.application.homePageModule.runtime_text.htmlText+="所使用的系统：";

				


				break;
			}


		}
		
		public function getRegisterListener(event:ResultEvent):void{
			loginRemoteObject.showBusyCursor=false;
			var rs:Array=event.result as Array;			
			var status:Boolean=Boolean(int(rs[0]));
			if(status==true){									
				//转到登录界面
				this.application.loginModule.currentState='';
				Alert.show(rs[1],'注册提示');
			}else{
				this.application.loginModule.password.text=this.application.loginModule.rePassword.text="";							
				Alert.show(rs[1],'注册提示');
			}
				

		}

		

		public function faultListener(event:FaultEvent):void{
			Alert.show(event.message.toString());
		}
		
		public function login(userName:String,password:String):void{
			loginRemoteObject.showBusyCursor=true;	
			this.userName=userName;		
			loginRemoteObject.login(userName,password);

		}
		public function register(userName:String,password:String,email:String):void{
			loginRemoteObject.showBusyCursor=true;
			loginRemoteObject.register(userName,password,email);
		}

	}
}