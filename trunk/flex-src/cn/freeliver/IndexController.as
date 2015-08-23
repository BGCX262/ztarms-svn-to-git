package cn.freeliver
{
	import mx.containers.Canvas;
	import mx.core.Application;
	import mx.controls.Alert;
	
	public class IndexController
	{
		private var application:Object;
		public function IndexController(application:Object)
		{
			this.application=application as Application;
		}
		public function addTabConvas():void{
			application.tabNavContainer.selectedIndex=1;
		}
		public function logout():void{
			this.application.loginModule.userName.text=this.application.loginModule.password.text='';
			this.application.currentState='';
		}

	}
}