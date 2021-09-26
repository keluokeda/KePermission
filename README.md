# KePermission
使用Kotlin方法参数和协程的Android权限请求

## 导入方式
#### 将JitPack存储库添加到您的构建文件中(项目根目录下build.gradle文件)
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
#### 添加依赖项
```
dependencies {
	        implementation 'com.github.keluokeda:RxImagePicker:1.0.1'
	}
```

## 使用
### 回调方式
```
 KePermission(supportFragmentManager)
                .requestPermissions(permissionList1) { list ->
                    list.forEach {
                        Logger.d(it)
                    }
                }
 ```
 ### 协程方式
 ```
  lifecycleScope.launch {
                val permission = KePermission(supportFragmentManager)
                val list = permission.requestPermissions(permissionList1)
                list.forEach {
                    Logger.d(it)
                }
            }
 ```
