使用须知：
        1.在使用  MyLibUtils.showCityDiaLogFragment（......）
            AndroidManifest 所在的Activity要加 android:theme="@style/CustomTheme"

        2.LiveEventBus 传值
        /**
           * 具体使用：
                https://github.com/JeremyLiao/LiveEventBus
           * */
        3.SharePrefense 使用：
            /**
             * 使用：
             * 1.AutoSharedPreferenceConfig.getInstance().init(this); 在Application里进行注册
             * 2.AppConfigSpSP.getInstance().setName("name");
             * 3.AppConfigSpSP.getInstance().getName();
             */
        4.BaseRecyclerViewAdapterHelper 导入如果报错
            在project.build
                allprojects {
                    repositories {
                        google()
                        jcenter()
                        maven { url "https://jitpack.io" }//加入这行
                    }
                }
        5.Xpop 使用：https://github.com/li-xiaojun/XPopup
            建议将源码和DemoApp下载下来
        