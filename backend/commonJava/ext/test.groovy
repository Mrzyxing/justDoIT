/**
 * Created by zyxing on 2019/7/10.
 */

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

d = DateTime.parse("2019-05-13 15:51:23", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))
print(d.dayOfWeek().get())