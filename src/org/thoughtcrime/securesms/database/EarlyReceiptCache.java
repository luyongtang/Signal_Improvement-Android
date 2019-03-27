package org.thoughtcrime.securesms.database;

import org.thoughtcrime.securesms.logging.Log;
import org.thoughtcrime.securesms.util.LRUCache;

import java.util.HashMap;
import java.util.Map;

public class EarlyReceiptCache {

  private static final String TAG = EarlyReceiptCache.class.getSimpleName();

  private final LRUCache<Long, Map<Address, Long>> cache = new LRUCache<>(100);

  public synchronized void increment(long timestamp, Address origin) {
    Log.i(TAG, this+"");
    Log.i(TAG, String.format("Early receipt: (%d, %s)", timestamp, origin.serialize()));

   //-------------------------catching the emoji int value representation-------
    String raw = Long.toString(timestamp);
    if (raw.length() > 13) {
      String real_time_stamp = (raw.substring(0, raw.length() - 2));
      String emoji_proxy = (raw.substring(raw.length() - 2, raw.length()));
      Log.i("real_time_stamp", real_time_stamp);
      Log.i("emoji_proxy", emoji_proxy);
    }

    //-------------------------end catching the emoji int value representation-------


    Map<Address, Long> receipts = cache.get(timestamp);

    if (receipts == null) {
      receipts = new HashMap<>();
    }

    Long count = receipts.get(origin);

    if (count != null) {
      receipts.put(origin, ++count);
    } else {
      receipts.put(origin, 1L);
    }

    cache.put(timestamp, receipts);
  }

  public synchronized Map<Address, Long> remove(long timestamp) {
    Map<Address, Long> receipts = cache.remove(timestamp);

    Log.i(TAG, this+"");
    Log.i(TAG, String.format("Checking early receipts (%d): %d", timestamp, receipts == null ? 0 : receipts.size()));

    return receipts != null ? receipts : new HashMap<>();
  }
}
