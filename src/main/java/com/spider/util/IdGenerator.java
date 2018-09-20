package com.spider.util;

import java.util.Calendar;
import java.util.Date;


public class IdGenerator {

	private static IdGenerator instance = null;

	private final static long twepoch = 1465803403579l;// Mon Jun 13 15:37 CST 2016
	// ������ʶλ��
	private final static long workerIdBits = 5L;
	// �������ı�ʶλ��
	private final static long datacenterIdBits = 5L;
	// ����ID���ֵ
	private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// ��������ID���ֵ
	private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// ����������λ
	private final static long sequenceBits = 12L;
	// ����IDƫ����12λ
	private final static long workerIdShift = sequenceBits;
	// ��������ID����17λ
	private final static long datacenterIdShift = sequenceBits + workerIdBits;
	// ʱ���������22λ
	private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;

	private long sequence = 0L;
	private final long workerId;
	private final long datacenterId;

	public IdGenerator(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();
		return genIdByTime(timestamp);
	}

	public synchronized long nextId(Date time) {
		long timestamp = timeGen();
		if (time != null) {
			timestamp = time.getTime();
		}
		return genIdByTime(timestamp);
	}

	protected long genIdByTime(long timestamp) {
		if (timestamp < lastTimestamp) {
			try {
				throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
			} catch (Exception e) {
				// SysLogger.warn("", e);
			}
		}

		if (lastTimestamp == timestamp) {
			// ��ǰ�����ڣ���+1
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// ��ǰ�����ڼ������ˣ���ȴ���һ��
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// IDƫ������������յ�ID��������ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;

		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static long getNextId() {
		return getInstance().nextId();
	}

	public static long getNextId(Date time) {
		return getInstance().nextId(time);
	}

	public static long getMinIdByDate(Date date) {
		return getInstance().getMinIdByDateInner(date);
	}

	public static long getMaxIdByDate(Date date) {
		return getInstance().getMaxIdByDateInner(date);
	}

	public static IdGenerator getInstance() {
		return instance;
	}

	public static long test() {
		final IdGenerator w = new IdGenerator(1, 0);
		return w.nextId();
	}

	public static long parseTime(long id) {
		return (id >> timestampLeftShift) + twepoch;
	}

	public static long parseFirstIndex(long id) {
		return parseTime(id) & 63;
	}

	public static long parseSecondIndex(long id) {
		return (parseTime(id) >> 6) & 63;
	}

	public long getMinIdByDateInner(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return genIdByTime(calendar.getTime().getTime());
	}

	public long getMaxIdByDateInner(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return genIdByTime(calendar.getTime().getTime()) - 1;
	}

	public static long getId(int n1,int n2) {
		instance = new IdGenerator(n1,n2);
		IdGenerator idg = IdGenerator.getInstance();
		return idg.getNextId();
		
	}
	public static void main(String[] args) {
		System.out.println(getId(0,10));	
	}
}
