package cn.freeliver{
	public class AccessList{
		private var _accessList:Array;

		public function AccessList(){
			_accessList=new Array('index');
		}
		
		public function getAccessList():Array{
			
			return this._accessList
		}
		
		public function setAccessList(list:String,delimiter:String):void{
			this._accessList=this._accessList.concat(list.split(delimiter));
		}
		public function setAccess(object:String):void{
			this._accessList.push(object);
		}
		public function hasCertification(certification:String):Boolean{
			if(this._accessList.indexOf(certification)!=-1) return true;
			else return false;
		}

		
	}
}