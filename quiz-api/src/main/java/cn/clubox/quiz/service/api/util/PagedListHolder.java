package cn.clubox.quiz.service.api.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PagedListHolder<E> {
	
	public static final int DEFAULT_PAGE_SIZE = 1;

	public static final int DEFAULT_MAX_LINKED_PAGES = 10;

	private List<E> source;

	private Date refreshDate;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private int page = 1;

	private boolean newPageSet;

	private int maxLinkedPages = DEFAULT_MAX_LINKED_PAGES;
	
	private int nrOfElements;


	/**
	 * Create a new holder instance.
	 * You'll need to set a source list to be able to use the holder.
	 * @see #setSource
	 */
	public PagedListHolder() {
		this(new ArrayList<E>(0));
	}

	/**
	 * Create a new holder instance with the given source list, starting with
	 * a default sort definition (with "toggleAscendingOnProperty" activated).
	 * @param source the source List
	 * @see MutableSortDefinition#setToggleAscendingOnProperty
	 */
	public PagedListHolder(List<E> source) {
		setSource(source);
	}


	/**
	 * Set the source list for this holder.
	 */
	public void setSource(List<E> source) {
		Objects.requireNonNull(source);
		this.source = source;
		this.refreshDate = new Date();
	}

	/**
	 * Return the source list for this holder.
	 */
	public List<E> getSource() {
		return this.source;
	}

	/**
	 * Return the last time the list has been fetched from the source provider.
	 */
	public Date getRefreshDate() {
		return this.refreshDate;
	}

	/**
	 * Set the current page size.
	 * Resets the current page number if changed.
	 * <p>Default value is 10.
	 */
	public void setPageSize(int pageSize) {
		if (pageSize != this.pageSize) {
			this.pageSize = pageSize;
			if (!this.newPageSet) {
				this.page = 0;
			}
		}
	}

	/**
	 * Return the current page size.
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * Set the current page number.
	 * Page numbering starts with 0.
	 */
	public void setPage(int page) {
		this.page = page;
		this.newPageSet = true;
	}

	/**
	 * Return the current page number.
	 * Page numbering starts with 1.
	 */
	public int getPage() {
		this.newPageSet = false;
		if (this.page >= getPageCount()) {
			this.page = getPageCount();
		}
		return this.page;
	}

	/**
	 * Set the maximum number of page links to a few pages around the current one.
	 */
	public void setMaxLinkedPages(int maxLinkedPages) {
		this.maxLinkedPages = maxLinkedPages;
	}

	/**
	 * Return the maximum number of page links to a few pages around the current one.
	 */
	public int getMaxLinkedPages() {
		return this.maxLinkedPages;
	}


	/**
	 * Return the number of pages for the current source list.
	 */
	public int getPageCount() {
		return (int)Math.ceil((double)getNrOfElements() / (double)getPageSize());
	}

	/**
	 * Return if the current page is the first one.
	 */
	public boolean isFirstPage() {
		return getPage() == 1;
	}

	/**
	 * Return if the current page is the last one.
	 */
	public boolean isLastPage() {
		return getPage() == getPageCount();
	}

	/**
	 * Switch to previous page.
	 * Will stay on first page if already on first page.
	 */
	public void previousPage() {
		if (!isFirstPage()) {
			this.page--;
		}
	}

	/**
	 * Switch to next page.
	 * Will stay on last page if already on last page.
	 */
	public void nextPage() {
		if (!isLastPage()) {
			this.page++;
		}
	}

	/**
	 * Return the total number of elements in the source list.
	 */
	public int getNrOfElements() {
		return nrOfElements;
	}
	
	public void setNrOfElements(int nrOfElements){
		this.nrOfElements = nrOfElements;
	}

	/**
	 * Return the element index of the first element on the current page.
	 * Element numbering starts with 0.
	 */
	public int getFirstElementOnPage() {
		return (getPageSize() * (getPage() -1));
	}

	/**
	 * Return the element index of the last element on the current page.
	 * Element numbering starts with 0.
	 */
	public int getLastElementOnPage() {
		int endIndex = getPageSize() * getPage();
		int size = getNrOfElements();
		return (endIndex > size ? size : endIndex) - 1;
	}

	/**
	 * Return a sub-list representing the current page.
	 */
//	public List<E> getPageList() {
//		return getSource().subList(getFirstElementOnPage(), getLastElementOnPage() + 1);
//	}

	/**
	 * Return the first page to which create a link around the current page.
	 */
	public int getFirstLinkedPage() {
		return Math.max(1, getPage() - (getMaxLinkedPages() / 2));
	}

	/**
	 * Return the last page to which create a link around the current page.
	 */
	public int getLastLinkedPage() {
		return Math.min(getFirstLinkedPage() + getMaxLinkedPages() - 1, getPageCount());
	}

}
