package org.seraph.lib.ui.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.seraph.lib.LibConfig
import org.seraph.lib.view.AdapterFootView
import org.seraph.lib.view.NoDataView

/**
 * ABaseAdapter
 * date：2019/4/19 18:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
abstract class ABaseAdapter<T : Any?, K : BaseViewHolder?> constructor(
        layoutResId: Int,
        data: List<T>?
) : BaseQuickAdapter<T, K>(layoutResId, data) {

    constructor(layoutResId: Int) : this(layoutResId, null)

    constructor(data: List<T>?) : this(0, data)


    /**
     * 数据页码
     */
    private var mPageNo: Int = 0


    /**
     * 当前请求页码
     */
    private var requestPageNo: Int = 1


    /**
     * 获取第一页
     */
    fun getOnePage(): Int {
        requestPageNo = 1
        return requestPageNo
    }

    /**
     * 获取下一页码
     */
    fun getNextPage(): Int {
        requestPageNo = mPageNo + 1
        return requestPageNo
    }

    /**
     * 默认的填充视图
     */
    private var noDataView: NoDataView? = null


    init {
        //默认打开加载更多
        this.setEnableLoadMore(true)
        //设置加载更多的自定义布局
        this.setLoadMoreView(object : AdapterFootView() {})
    }


    /**
     * 设置填充视图
     */
    fun setNoDataView(noDataView: NoDataView) {
        this.noDataView = noDataView
        this.emptyView = this.noDataView
    }

    /**
     * 获取填充视图
     */
    fun getNoDataView(): NoDataView? {
        return this.noDataView
    }

    /**
     * 更新数据
     */
    fun onUpdateList(list: List<T>?) {
        onUpdateList(list, null)
    }

    fun onUpdateList(list: List<T>?, pageSize: Int?) {
        if (list == null) { //加载失败
            //如果是第一页
            if (requestPageNo == 1) {
                noDataView?.setNetErr()
            }
            loadMoreFail()
            return
        }

        //判断是新数据还是加更多数据
        when (requestPageNo) {
            1 -> {
                setNewData(list)
            }
            else -> {
                addData(list)
            }
        }

        //判断加载更多的状态
        when {
            list.size >= (pageSize ?: LibConfig.PAGE_SIZE) -> { //加载完成
                if (requestPageNo == 1) {
                    noDataView?.setLoadingOk()
                }
                loadMoreComplete()
            }
            else -> {
                if (requestPageNo == 1 && list.isEmpty()) {
                    noDataView?.setNoDate()
                }
                loadMoreEnd() //加载结束
            }

        }

        mPageNo = requestPageNo
    }


}