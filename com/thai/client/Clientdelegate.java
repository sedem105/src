package com.thai.client;

import java.util.List;

public interface Clientdelegate {

	public void update(String info);

    public void updateTable(List<String> list);

    public void setReady(String name);

    public void setUnReady(String name);

    public void kick();

    public void createConfig(String config);
}
