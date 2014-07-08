/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: ArgumentList.java,v 1.3 2004/02/16 22:24:29 minchau Exp $
 */

package org.apache.xalan.xsltc.compiler;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
final class ArgumentList {
    private final Expression   _arg;
    private final ArgumentList _rest;
	
    public ArgumentList(Expression arg, ArgumentList rest) {
	_arg = arg;
	_rest = rest;
    }
		
    public String toString() {
	return _rest == null
	    ? _arg.toString()
	    : _arg.toString() + ", " + _rest.toString();
    }
}
