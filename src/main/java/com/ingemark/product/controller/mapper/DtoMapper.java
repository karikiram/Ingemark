package com.ingemark.product.controller.mapper;

public interface DtoMapper<T1, T2> {
	/**
	 * Converts an object of type T1 to an object of type T2.
	 *
	 * @param t1 the object to convert
	 * @return the converted object of type T2
	 */
	T2 mapTo(T1 t1);

	/**
	 * Converts an object of type T2 back to an object of type T1.
	 *
	 * @param t2 the object to convert
	 * @return the converted object of type T1
	 */
	T1 mapFrom(T2 t2);
}
