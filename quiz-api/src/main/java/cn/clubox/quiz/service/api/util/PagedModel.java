package cn.clubox.quiz.service.api.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagedModel<E> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4884787652802705044L;

	private int page;
	private int nrOfElements;
	private List<E> source;
	
	public PagedModel(){
		new PagedModel<>(new ArrayList<>());
	}
	
	public PagedModel(List<E> source){
		this.source = source;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNrOfElements() {
		return nrOfElements;
	}

	public void setNrOfElements(int nrOfElements) {
		this.nrOfElements = nrOfElements;
	}

	public List<E> getSource() {
		return source;
	}

	public void setSource(List<E> source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "PageableModel [getPage()=" + getPage() + ", getNrOfElements()=" + getNrOfElements() + ", getSource()="
				+ getSource() + "]";
	}
	
}
