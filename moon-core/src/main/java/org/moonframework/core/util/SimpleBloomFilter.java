package org.moonframework.core.util;

import java.io.*;
import java.util.BitSet;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
public class SimpleBloomFilter {

    private static final int[] SEEDS = new int[]{7, 11, 13, 31, 37, 61, 71};

    private int size;
    private BitSet bitSet;
    private SimpleHash[] func = new SimpleHash[SEEDS.length];

    public SimpleBloomFilter() {
        this(new BitSet(2 << 28));

    }

    public SimpleBloomFilter(BitSet bitSet) {
        this.bitSet = bitSet;
        this.size = bitSet.size();
        bitSet.length();
        for (int i = 0; i < SEEDS.length; i++)
            func[i] = new SimpleHash(size, SEEDS[i]);
    }

    /**
     * @param filename
     */
    public void saveBit(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename), false))) {
            oos.writeObject(bitSet);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     */
    public void readBit(String filename) {
        File file = new File(filename);
        if (!file.exists())
            return;

        bitSet.clear();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            bitSet = (BitSet) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void add(String value) {
        for (SimpleHash f : func)
            bitSet.set(f.hash(value), true);
    }

    public boolean contains(String value) {
        if (value == null)
            return false;
        for (SimpleHash f : func)
            if (!bitSet.get(f.hash(value))) return false;
        return true;
    }

    public static class SimpleHash {

        private int capacity;
        private int seed;

        public SimpleHash(int capacity, int seed) {
            this.capacity = capacity;
            this.seed = seed;
        }

        public int hash(String value) {
            int p = 16777619;
            int hash = -2128831035;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                hash = (hash ^ value.charAt(i)) * p * seed;
            }
            hash += hash << 13;
            hash ^= hash >> 7;
            hash += hash << 3;
            hash ^= hash >> 17;
            hash += hash << 5;
            hash = hash ^ (hash >> 10) ^ (hash >> 20);
            return (capacity - 1) & hash;
        }
    }

}
