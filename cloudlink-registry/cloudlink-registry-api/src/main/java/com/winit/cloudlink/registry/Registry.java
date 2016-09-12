package com.winit.cloudlink.registry;

import com.winit.cloudlink.common.Node;
import com.winit.cloudlink.registry.support.AbstractRegistry;

/**
 * Registry. (SPI, Prototype, ThreadSafe)
 * 
 * @see RegistryFactory#getRegistry(URL)
 * @see AbstractRegistry
 */
public interface Registry extends Node, RegistryService {
}
