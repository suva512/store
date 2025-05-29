package com.ezee.store.exception;

public enum ErrorCode {
	ID_NOT_FOUND_EXCEPTION(111, "Id not Found"), EMPTY_RESULT_DATA_EXCEPTION(112, "list is empty"),
	KEY_NOT_FOUND_EXCEPTION(113, "Key not Found"), EMAIL_FOUND_EXCEPTION(114, "Email already Exists in the database."),
	UPDATE_FAILED_EXCEPTION(115,"Update failed"),ID_ALREADY_EXISTS(115,"Id Already EXists in the database."),
	DATABASE_ERROR(116, "Database operation failed.");
	
	private String message;
	private int code;

	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}

