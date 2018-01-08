package com.ffraporti.desafio;

public enum ResponseMessages {
	
	NOT_FOUND {
		public String toString() {
			return "Object was not found";
		}
	},
	
	NO_BODY_PROVIDED {
		public String toString() {
			return "Wrong message body. Check if the JSON content of the message fulfill all the requirements.";
		}
	},
	
	RESOURCE_ALREADY_PRESENT {
		public String toString() {
			return "The resource can't be added due to a conflict. This resource is already present.";
		}
	},
	
	RESOURCE_NOT_PRESENT {
		public String toString() {
			return "The resource can't be updated, it was not found on the system.";
		}
	},
	
	SUCCESSFULLY_ADDED {
		public String toString() {
			return "The resource was successfully added to the system.";
		}
	},
	
	SUCCESSFULLY_REMOVED {
		public String toString() {
			return "The resource was successfully removed from the system.";
		}
	},
	
	SUCCESSFULLY_UPDATED {
		public String toString() {
			return "The resource was successfully updated.";
		}
	}

}
