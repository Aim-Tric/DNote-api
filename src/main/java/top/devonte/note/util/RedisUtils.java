package top.devonte.note.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private ListOps listOps;
    private HashOps hashOps;
    private ValueOps valueOps;
    private SetOps setOps;
    private ZSetOps zSetOps;

    @PostConstruct
    public void init() {
        listOps = new ListOps();
        hashOps = new HashOps();
        valueOps = new ValueOps();
        setOps = new SetOps();
    }

    public ListOps getListOps() {
        return listOps;
    }

    public HashOps getHashOps() {
        return hashOps;
    }

    public ValueOps getValueOps() {
        return valueOps;
    }

    public SetOps getSetOps() {
        return setOps;
    }

    public ZSetOps getzSetOps() {
        return zSetOps;
    }

    public class ListOps {
        private final ListOperations<String, Object> ops = redisTemplate.opsForList();

        public void add(String k, Object v) {
            ops.rightPush(k, v);
        }

        public void addAll(String k, List<Object> v) {
            ops.rightPushAll(k, v);
        }

        public List<Object> get(String k) {
            Long size = ops.size(k);
            if (size == null) {
                size = 0L;
            }
            return ops.range(k, 0, size);
        }

        public ListOperations<String, Object> getOps() {
            return ops;
        }

    }

    public class HashOps {
        private final HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        public void put(String k, Object hk, Object hv) {
            ops.put(k, hk, hv);
        }

        public void putIfAbsent(String k, Object hk, Object hv) {
            if (!ops.hasKey(k, hk)) {
                ops.put(k, hk, hv);
            }
        }

        public void putWithExpire(String k, Object hk, Object hv, long t) {
            put(k, hk, hv);
            setExpire(k, t);
        }

        public void putIfAbsentWithExpire(String k, Object hk, Object hv, long t) {
            putIfAbsent(k, hk, hv);
            setExpire(k, t);
        }

        private void setExpire(String k, long t) {
            redisTemplate.expire(k, t, TimeUnit.SECONDS);
        }

        public Object get(String k, Object hk) {
            return ops.get(k, hk);
        }

        public HashOperations<String, Object, Object> getOps() {
            return ops;
        }
    }

    public class ValueOps {
        private final ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        public void set(String k, Object v) {
            ops.set(k, v);
        }

        public void set(String k, Object v, int sec) {
            ops.set(k, v, sec, TimeUnit.SECONDS);
        }

        public Object get(String k) {
            return ops.get(k);
        }

        public ValueOperations<String, Object> getOps() {
            return ops;
        }
    }

    public class SetOps {
        private final SetOperations<String, Object> ops = redisTemplate.opsForSet();

        public void add(String k, Object... v) {
            ops.add(k, v);
        }

        public Object get(String k) {
            return ops.pop(k);
        }

        public Set<Object> getAll(String k) {
            return ops.members(k);
        }

        public SetOperations<String, Object> getOps() {
            return ops;
        }
    }

    public class ZSetOps {
        private ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();

        public ZSetOperations<String, Object> getOps() {
            return ops;
        }
    }

}
