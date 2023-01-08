// 使用技术：vuex
// 作用：控制左侧菜单栏的隐藏与展开、顶部导航栏的面包屑显示
export default{
    state:{
        // 控制菜单栏的状态
        isCollapse:false,
        tabsList:[//面包屑数据
            {
                path: '/',
                name:'firstPage',
                label:'首页',
                icon:'s-home',
                url:'firstPage'
            }
        ]
    },
    mutations:{
        // 反转菜单栏的状态
        collapseChange(state){
            state.isCollapse=!state.isCollapse
        },
        // 更新面包屑的数据
        menuChange(state,val){
            // val是菜单对象数据
            if(val.name!='main'){
                const index = state.tabsList.findIndex(item =>item.name===val.name)
                if(index === -1){
                    state.tabsList.push(val)
                }
            }
        },
        closeTag(state,val){
           const index = state.tabsList.findIndex(item => item.name===val.name)
           state.tabsList.splice(index,1)

        }
    }
}