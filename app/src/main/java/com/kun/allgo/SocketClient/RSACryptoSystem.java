package com.kun.allgo.SocketClient;

/**
 * Created by Duy on 14-Apr-16.
 */
public class RSACryptoSystem {
    private long p, q, n, t, flag;
    int i, j;
    private long[] e, d, m, en;
    private char[] msg;
    private int size;

    public RSACryptoSystem() {
        msg = new char[100];
        e = new long[100];
        d = new long[100];
        m = new long[100];
        en = new long[100];
    }

    int prime(long pr) {
        int i;
        j = (int) Math.sqrt(pr);
        for (i = 2; i <= j; i++)
        {
            if (pr % i == 0)
                return 0;
        }
        return 1;
    }
    void ce() {
        int k;
        k = 0;
        for (i = 2; i < t; i++)
        {
            if (t % i == 0)
                continue;
            flag = prime(i);
            if (flag == 1 && i != p && i != q)
            {
                e[k] = i;
                flag = cd(e[k]);
                if (flag > 0)
                {
                    d[k] = flag;
                    k++;
                }
                if (k == 99)
                    break;
            }
        }
    }
    long cd(long x)
    {
        long k = 1;
        while (1==1)
        {
            k = k + t;
            if (k % x == 0)
                return (k / x);
        }
    }

    void genkey()
    {
        for (i = 0; i < size; i++)
            m[i] = msg[i];
        n = p * q;
        t = (p - 1) * (q - 1);
        ce();
    }
    void encrypt()
    {
        long pt, ct, key = e[0], k, len;
        i = 0;
        len = size;
        while (i != len)
        {
            pt = m[i];
            pt = pt - 96;
            k = 1;
            for (j = 0; j < key; j++)
            {
                k = k * pt;
                k = k % n;
            }
            ct = k + 96;
            en[i] = ct;
            i++;
        }
        en[i] = -1;
    }
    void decrypt()
    {
        long pt, ct, key = d[0], k;
        i = 0;
        while (en[i] != -1)
        {
            ct = en[i]-96;
            k = 1;
            for (j = 0; j < key; j++)
            {
                k = k * ct;
                k = k % n;
            }
            pt = k + 96;
            m[i] = pt;
            i++;
        }
        m[i] = -1;
    }

    public long getP() {
        return p;
    }

    public void setP(long p) {
        this.p = p;
    }

    public long getQ() {
        return q;
    }

    public void setQ(long q) {
        this.q = q;
    }

    public long getN() {
        return n;
    }

    public void setN(long n) {
        this.n = n;
    }

    public long[] getE() {
        return e;
    }

    public void setE(long[] e) {
        this.e = e;
    }

    public long[] getD() {
        return d;
    }

    public void setD(long[] d) {
        this.d = d;
    }

    public long[] getM() {
        return m;
    }

    public void setM(long[] m) {
        this.m = m;
    }

    public long[] getEn() {
        return en;
    }

    public void setEn(long[] en) {
        this.en = en;
    }

    public char[] getMsg() {
        return msg;
    }

    public void setMsg(char[] msg) {
        this.msg = msg;
        for (int id = 0; id < size; id++)
        {
            m[id] = msg[id];
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}