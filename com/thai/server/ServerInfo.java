package com.thai.server;

import java.util.List;
import java.util.Set;

public class ServerInfo {

    private Set<String> readyList;
    private Set<String> configs;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	private List<String>list;

    public Set<String> getReadyList() {
        return readyList;
    }

    public void setReadyList(final Set<String> readyList) {
        this.readyList = readyList;
    }

    public Set<String> getConfigs() {
        return configs;
    }

    public void setConfigs(final Set<String> configs) {
        this.configs = configs;
    }
}
