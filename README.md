# android-conversions

[![Join the chat at https://gitter.im/pfn/android-conversions](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/pfn/android-conversions?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Generated implicit conversions and extension functions for the Android
framework in Scala

## Description

All interfaces and abstract classes with a single abstract method have been
wrapped by an implicit conversion for the corresponding function.

On top of that, all methods that take these objects as a single parameter
have been enhanced to take an automatically typed function or by-name
parameter.

The extension functions are named with any prefixed `set` and suffixed
`Listener` names removed. Additionally, the remainder of the name is
decapitalized. For example `setOnClickListener` becomes `onClick` and
`setInterpolator` becomes `interpolator`.

When there are are callbacks with arguments that can be ignored, one can
invoke the `0` variant of the extension function, such as `onClick0` to pass
a `=> A` by-name parameter

`1.3+`: If there is a naming collision in an existing method name, such as
with `TextView.onEditorAction`, the `asScala` extension function will wrap
the object with the extension wrapper, and it can be called without
encountering the naming conflict. e.g.
`textview.asScala.onEditorAction((a,b,c) => true)`; this has no allocation
cost.

## Use it from SBT

Base android framework extensions:

`libraryDependencies += "com.hanhuy.android" %% "scala-conversions" % "23.1.1"`

Extensions for `support-v4`:

`libraryDependencies += "com.hanhuy.android" %% "scala-conversions-v4" % "23.1.1"`

Extensions for `appcompat-v7`:

`libraryDependencies += "com.hanhuy.android" %% "scala-conversions-appcompat" % "23.1.1"`

Extensions for `design`:

`libraryDependencies += "com.hanhuy.android" %% "scala-conversions-design" % "23.1.1"`

Versions will generally track supportSdkVersion numbers, suffixed with letters
to represent local fixes. Other versions can be generated upon request.

## Use it from code

`import com.hanhuy.android.extensions._`

`findViewById(R.id.something).onClick { v => Toast.makeText(...).show() }`

or

`findViewById(R.id.something).onClick0 { Toast.makeText(...).show() }`

`support-v4`:

```
import com.hanhuy.android.v4.extensions._
import com.hanhuy.android.v4.conversions._
```

`appcompat-v7`:

```
import com.hanhuy.android.appcompat.extensions._
import com.hanhuy.android.appcompat.conversions._
```

`design`:

```
import com.hanhuy.android.design.extensions._
import com.hanhuy.android.design.conversions._
```

From `1.2` onward, generated sources are published to sonatype and browsable
from IntelliJ.

## generated code

https://gist.github.com/pfn/b4dc88b80e846f1fbac1
