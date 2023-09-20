package com.wlovec.welovecodeapi.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public class ExceptionMessage {

	private String date;
	private String message;
	private String path;
	private String className;
	private int status;
	private int timestamp;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public ExceptionMessage() {
		super();
	}

	public ExceptionMessage(String date, String message, String path, String className, int status,
			int timestamp) {
		super();
		this.date = date;
		this.message = message;
		this.path = path;
		this.className = className;
		this.status = status;
		this.timestamp = timestamp;
	}

	public ExceptionMessage build() {
		return new ExceptionMessage(date, message, path, className, status, timestamp);
	}
}
