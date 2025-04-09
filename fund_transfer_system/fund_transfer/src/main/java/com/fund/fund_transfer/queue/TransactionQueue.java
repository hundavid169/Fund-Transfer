package com.fund.fund_transfer.queue;

import com.fund.fund_transfer.model.TransactionLog;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TransactionQueue {
    private final BlockingQueue<TransactionLog> queue = new LinkedBlockingQueue<>();

    public void submit(TransactionLog tx) throws InterruptedException {
        queue.put(tx);
    }

    public TransactionLog take() throws InterruptedException {
        return queue.take();
    }
}
