package com.eyoubika.common;

import java.util.ArrayList;
import java.util.List;

/*==========================================================================================*
 * Description:	堆排序，解决TopK问题
 * Class:		HeapSort
 * Author:		lijiaxuan
 * Copyright:	ABC (c) 2015 ljx728@gmail.com
 * History:		1.0 created by lijiaxuan at 2015年10月20日 上午8:04:49
 *==========================================================================================*/
public class HeapSort {
	List<RankData> rankDataList;

	public List<RankData> getRankDataList() {
		return rankDataList;
	}

	public void setRankDataList(List<RankData> rankDataList) {
		this.rankDataList = rankDataList;
	}

	public HeapSort() {
		rankDataList = new ArrayList<RankData>();
	}

	public void init(String id, double dataD, String type) {
		RankData RankData = new RankData(id, dataD);
		rankDataList.add(RankData);
	}

	public void init(String id, double dataD, String other, String type) {
		RankData RankData = new RankData(id, dataD, other);
		rankDataList.add(RankData);
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		rankBillboard
	 * Author:		1.0 created by lijiaxuan at 2015年10月20日 上午9:44:09
	 *--------------------------------------------------------------------------------------*/
	public List<RankData> sort(String type) {
		List<RankData> resList = new ArrayList<RankData>();
		int rankDataListSize = rankDataList.size();
		double minDataD;
		double nowDataD;
		if (rankDataListSize < CommonVariables.RANKLIST_ITEMS_MAX) {
			return this.rankDataList;
		}
		for (int i = 0; i < CommonVariables.RANKLIST_ITEMS_MAX; i++) {
			resList.add(this.rankDataList.get(i));
		}
		buildHeap(resList);
		for (int i = CommonVariables.RANKLIST_ITEMS_MAX; i < rankDataListSize; i++) {

			minDataD = resList.get(0).getDataD(); // 小根堆，最小值是第一个
			nowDataD = rankDataList.get(i).getDataD();

			if (nowDataD > minDataD) {
				resList.set(0, rankDataList.get(i));
				for (int j = CommonVariables.RANKLIST_ITEMS_MAX / 2 - 1; j >= 0; j--) { // i从第一个非叶子结点开始
					minHeapify(resList, j, CommonVariables.RANKLIST_ITEMS_MAX);
				}
			}
		}

		heapMain(resList); // 变成顺序的列表
		return resList;
	}

	public void buildHeap(List<RankData> resList) {
		// 构建最小堆
		// 完全二叉树只有数组下标小于或等于 (data.length) / 2 - 1 的元素有孩子结点，遍历这些结点。
		for (int i = CommonVariables.RANKLIST_ITEMS_MAX / 2 - 1; i >= 0; i--) {// i从第一个非叶子结点开始
			minHeapify(resList, i, CommonVariables.RANKLIST_ITEMS_MAX);
		}
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		minHeapify
	 * Author:		1.0 created by lijiaxuan at 2015年10月16日 下午5:47:17
	 *--------------------------------------------------------------------------------------*/
	public void minHeapify(List<RankData> resList, int i, int heapSize) {
		int minIndex = i;
		int leftIndex = 2 * i + 1;
		int rightIndex = 2 * i + 2;

		double leftData = 0;
		double rightData = 0;
		double minData = 0;
		minData = resList.get(minIndex).getDataD();
		if (leftIndex <= heapSize - 1) {
			leftData = resList.get(leftIndex).getDataD();
			if (leftData < minData) {
				minIndex = leftIndex;
			}
		}
		minData = resList.get(minIndex).getDataD();
		if (rightIndex <= heapSize - 1) {
			rightData = resList.get(rightIndex).getDataD();
			if (rightData < minData) {
				minIndex = rightIndex;
			}
		}

		if (minIndex != i) {
			RankData rankData = resList.get(minIndex);
			resList.set(minIndex, resList.get(i));
			resList.set(i, rankData);
			minHeapify(resList, minIndex, heapSize);
		}

	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		heapMain
	 * Author:		1.0 created by lijiaxuan at 2015年10月16日 下午5:47:09
	 *--------------------------------------------------------------------------------------*/
	public void heapMain(List<RankData> resList) {
		int heapSize = CommonVariables.RANKLIST_ITEMS_MAX;
		for (int i = CommonVariables.RANKLIST_ITEMS_MAX - 1; i > 0; i--) {
			RankData rankData = resList.get(0); // a[minIndex];
			resList.set(0, resList.get(i));
			resList.set(i, rankData);
			heapSize -= 1;
			minHeapify(resList, 0, heapSize); // 在heapSize范围内根结点的左右子树都已经是最大堆
		}
	}

}