# master-widget
## master-widget-dialog
[ ![Download](https://api.bintray.com/packages/ooftf/maven/master-widget-dialog/images/download.svg) ](https://bintray.com/ooftf/maven/master-widget-dialog/_latestVersion)
## master-widget-suspend-window
[ ![Download](https://api.bintray.com/packages/ooftf/maven/master-widget-suspend-window/images/download.svg) ](https://bintray.com/ooftf/maven/master-widget-suspend-window/_latestVersion)
## master-widget-toolbar
[ ![Download](https://api.bintray.com/packages/ooftf/maven/master-widget-toolbar/images/download.svg) ](https://bintray.com/ooftf/maven/master-widget-toolbar/_latestVersion)
## master-widget-statelayout
[ ![master-widget-statelayout](https://api.bintray.com/packages/ooftf/maven/master-widget-statelayout/images/download.svg) ](https://bintray.com/ooftf/maven/master-widget-statelayout/_latestVersion)

## arch-frame-mvvm
[ ![arch-frame-mvvm](https://api.bintray.com/packages/ooftf/maven/arch-frame-mvvm/images/download.svg) ](https://bintray.com/ooftf/maven/arch-frame-mvvm/_latestVersion)
### 混淆
    -keepclassmembers  class * extends androidx.lifecycle.AndroidViewModel {
             <init>(...);
        }
    -keepclassmembers public class * extends androidx.databinding.ViewDataBinding{
        public static  inflate(android.view.LayoutInflater);
        public static  inflate(android.view.LayoutInflater,android.view.ViewGroup,boolean);
    }