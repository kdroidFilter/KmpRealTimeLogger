/*
 * ATTENTION: An "eval-source-map" devtool has been used.
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file with attached SourceMaps in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory();
	else if(typeof define === 'function' && define.amd)
		define([], factory);
	else if(typeof exports === 'object')
		exports["kmplog-client"] = factory();
	else
		root["kmplog-client"] = factory();
})(globalThis, () => {
return /******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "../../node_modules/@js-joda/core/dist/js-joda.esm.js":
/*!************************************************************!*\
  !*** ../../node_modules/@js-joda/core/dist/js-joda.esm.js ***!
  \************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


/***/ }),

/***/ "./kotlin/skiko.wasm":
/*!***************************!*\
  !*** ./kotlin/skiko.wasm ***!
  \***************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

module.exports = __webpack_require__.p + "2eaba8643e2ccdf352b4.wasm";

/***/ }),

/***/ "./kotlin/composeApp.mjs":
/*!*******************************!*\
  !*** ./kotlin/composeApp.mjs ***!
  \*******************************/
/***/ ((__webpack_module__, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.a(__webpack_module__, async (__webpack_handle_async_dependencies__, __webpack_async_result__) => { try {\n__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   _initialize: () => (/* binding */ _initialize),\n/* harmony export */   \"default\": () => (__WEBPACK_DEFAULT_EXPORT__),\n/* harmony export */   main: () => (/* binding */ main),\n/* harmony export */   memory: () => (/* binding */ memory)\n/* harmony export */ });\n/* harmony import */ var _skiko_mjs__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./skiko.mjs */ \"./kotlin/skiko.mjs\");\n/* harmony import */ var _js_joda_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @js-joda/core */ \"../../node_modules/@js-joda/core/dist/js-joda.esm.js\");\n/* harmony import */ var _composeApp_uninstantiated_mjs__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./composeApp.uninstantiated.mjs */ \"./kotlin/composeApp.uninstantiated.mjs\");\nvar __webpack_async_dependencies__ = __webpack_handle_async_dependencies__([_skiko_mjs__WEBPACK_IMPORTED_MODULE_0__]);\n_skiko_mjs__WEBPACK_IMPORTED_MODULE_0__ = (__webpack_async_dependencies__.then ? (await __webpack_async_dependencies__)() : __webpack_async_dependencies__)[0];\n\n\n\n\n\nconst exports = (await (0,_composeApp_uninstantiated_mjs__WEBPACK_IMPORTED_MODULE_2__.instantiate)({\n    './skiko.mjs': _skiko_mjs__WEBPACK_IMPORTED_MODULE_0__,\n    '@js-joda/core': _js_joda_core__WEBPACK_IMPORTED_MODULE_1__\n})).exports;\n\n/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (new Proxy(exports, {\n    _shownError: false,\n    get(target, prop) {\n        if (!this._shownError) {\n            this._shownError = true;\n            throw new Error(\"Do not use default import. Use the corresponding named import instead.\")\n        }\n    }\n}));\nconst {\n    main,\n    _initialize,\n    memory\n} = exports;\n\n\n__webpack_async_result__();\n} catch(e) { __webpack_async_result__(e); } }, 1);//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiLi9rb3RsaW4vY29tcG9zZUFwcC5tanMiLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7QUFDK0M7QUFDVztBQUNJOztBQUU5RCx1QkFBdUIsMkVBQVc7QUFDbEMsbUJBQW1CLHVDQUFlO0FBQ2xDLHFCQUFxQiwwQ0FBd0I7QUFDN0MsQ0FBQzs7QUFFRCxpRUFBZTtBQUNmO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQyxDQUFDLEVBQUM7QUFDSTtBQUNQO0FBQ0E7QUFDQTtBQUNBLEVBQUUiLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly9rbXBsb2ctY2xpZW50Ly4va290bGluL2NvbXBvc2VBcHAubWpzPzY0MTAiXSwic291cmNlc0NvbnRlbnQiOlsiXG5pbXBvcnQgKiBhcyBMaTl6YTJscmJ5NXRhbk0gZnJvbSAnLi9za2lrby5tanMnO1xuaW1wb3J0ICogYXMgX3JlZl9RR3B6TFdwdlpHRXZZMjl5WlFfIGZyb20gJ0Bqcy1qb2RhL2NvcmUnO1xuaW1wb3J0IHsgaW5zdGFudGlhdGUgfSBmcm9tICcuL2NvbXBvc2VBcHAudW5pbnN0YW50aWF0ZWQubWpzJztcblxuY29uc3QgZXhwb3J0cyA9IChhd2FpdCBpbnN0YW50aWF0ZSh7XG4gICAgJy4vc2tpa28ubWpzJzogTGk5emEybHJieTV0YW5NLFxuICAgICdAanMtam9kYS9jb3JlJzogX3JlZl9RR3B6TFdwdlpHRXZZMjl5WlFfXG59KSkuZXhwb3J0cztcblxuZXhwb3J0IGRlZmF1bHQgbmV3IFByb3h5KGV4cG9ydHMsIHtcbiAgICBfc2hvd25FcnJvcjogZmFsc2UsXG4gICAgZ2V0KHRhcmdldCwgcHJvcCkge1xuICAgICAgICBpZiAoIXRoaXMuX3Nob3duRXJyb3IpIHtcbiAgICAgICAgICAgIHRoaXMuX3Nob3duRXJyb3IgPSB0cnVlO1xuICAgICAgICAgICAgdGhyb3cgbmV3IEVycm9yKFwiRG8gbm90IHVzZSBkZWZhdWx0IGltcG9ydC4gVXNlIHRoZSBjb3JyZXNwb25kaW5nIG5hbWVkIGltcG9ydCBpbnN0ZWFkLlwiKVxuICAgICAgICB9XG4gICAgfVxufSk7XG5leHBvcnQgY29uc3Qge1xuICAgIG1haW4sXG4gICAgX2luaXRpYWxpemUsXG4gICAgbWVtb3J5XG59ID0gZXhwb3J0cztcblxuIl0sIm5hbWVzIjpbXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///./kotlin/composeApp.mjs\n");

/***/ }),

/***/ "./kotlin/composeApp.uninstantiated.mjs":
/*!**********************************************!*\
  !*** ./kotlin/composeApp.uninstantiated.mjs ***!
  \**********************************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {


/***/ }),

/***/ "./kotlin/skiko.mjs":
/*!**************************!*\
  !*** ./kotlin/skiko.mjs ***!
  \**************************/
/***/ ((__webpack_module__, __webpack_exports__, __webpack_require__) => {


/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = __webpack_modules__;
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/async module */
/******/ 	(() => {
/******/ 		var webpackQueues = typeof Symbol === "function" ? Symbol("webpack queues") : "__webpack_queues__";
/******/ 		var webpackExports = typeof Symbol === "function" ? Symbol("webpack exports") : "__webpack_exports__";
/******/ 		var webpackError = typeof Symbol === "function" ? Symbol("webpack error") : "__webpack_error__";
/******/ 		var resolveQueue = (queue) => {
/******/ 			if(queue && queue.d < 1) {
/******/ 				queue.d = 1;
/******/ 				queue.forEach((fn) => (fn.r--));
/******/ 				queue.forEach((fn) => (fn.r-- ? fn.r++ : fn()));
/******/ 			}
/******/ 		}
/******/ 		var wrapDeps = (deps) => (deps.map((dep) => {
/******/ 			if(dep !== null && typeof dep === "object") {
/******/ 				if(dep[webpackQueues]) return dep;
/******/ 				if(dep.then) {
/******/ 					var queue = [];
/******/ 					queue.d = 0;
/******/ 					dep.then((r) => {
/******/ 						obj[webpackExports] = r;
/******/ 						resolveQueue(queue);
/******/ 					}, (e) => {
/******/ 						obj[webpackError] = e;
/******/ 						resolveQueue(queue);
/******/ 					});
/******/ 					var obj = {};
/******/ 					obj[webpackQueues] = (fn) => (fn(queue));
/******/ 					return obj;
/******/ 				}
/******/ 			}
/******/ 			var ret = {};
/******/ 			ret[webpackQueues] = x => {};
/******/ 			ret[webpackExports] = dep;
/******/ 			return ret;
/******/ 		}));
/******/ 		__webpack_require__.a = (module, body, hasAwait) => {
/******/ 			var queue;
/******/ 			hasAwait && ((queue = []).d = -1);
/******/ 			var depQueues = new Set();
/******/ 			var exports = module.exports;
/******/ 			var currentDeps;
/******/ 			var outerResolve;
/******/ 			var reject;
/******/ 			var promise = new Promise((resolve, rej) => {
/******/ 				reject = rej;
/******/ 				outerResolve = resolve;
/******/ 			});
/******/ 			promise[webpackExports] = exports;
/******/ 			promise[webpackQueues] = (fn) => (queue && fn(queue), depQueues.forEach(fn), promise["catch"](x => {}));
/******/ 			module.exports = promise;
/******/ 			body((deps) => {
/******/ 				currentDeps = wrapDeps(deps);
/******/ 				var fn;
/******/ 				var getResult = () => (currentDeps.map((d) => {
/******/ 					if(d[webpackError]) throw d[webpackError];
/******/ 					return d[webpackExports];
/******/ 				}))
/******/ 				var promise = new Promise((resolve) => {
/******/ 					fn = () => (resolve(getResult));
/******/ 					fn.r = 0;
/******/ 					var fnQueue = (q) => (q !== queue && !depQueues.has(q) && (depQueues.add(q), q && !q.d && (fn.r++, q.push(fn))));
/******/ 					currentDeps.map((dep) => (dep[webpackQueues](fnQueue)));
/******/ 				});
/******/ 				return fn.r ? promise : getResult();
/******/ 			}, (err) => ((err ? reject(promise[webpackError] = err) : outerResolve(exports)), resolveQueue(queue)));
/******/ 			queue && queue.d < 0 && (queue.d = 0);
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/global */
/******/ 	(() => {
/******/ 		__webpack_require__.g = (function() {
/******/ 			if (typeof globalThis === 'object') return globalThis;
/******/ 			try {
/******/ 				return this || new Function('return this')();
/******/ 			} catch (e) {
/******/ 				if (typeof window === 'object') return window;
/******/ 			}
/******/ 		})();
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/publicPath */
/******/ 	(() => {
/******/ 		var scriptUrl;
/******/ 		if (__webpack_require__.g.importScripts) scriptUrl = __webpack_require__.g.location + "";
/******/ 		var document = __webpack_require__.g.document;
/******/ 		if (!scriptUrl && document) {
/******/ 			if (document.currentScript)
/******/ 				scriptUrl = document.currentScript.src;
/******/ 			if (!scriptUrl) {
/******/ 				var scripts = document.getElementsByTagName("script");
/******/ 				if(scripts.length) {
/******/ 					var i = scripts.length - 1;
/******/ 					while (i > -1 && (!scriptUrl || !/^http(s?):/.test(scriptUrl))) scriptUrl = scripts[i--].src;
/******/ 				}
/******/ 			}
/******/ 		}
/******/ 		// When supporting browsers where an automatic publicPath is not supported you must specify an output.publicPath manually via configuration
/******/ 		// or pass an empty string ("") and set the __webpack_public_path__ variable from your code to use your own logic.
/******/ 		if (!scriptUrl) throw new Error("Automatic publicPath is not supported in this browser");
/******/ 		scriptUrl = scriptUrl.replace(/#.*$/, "").replace(/\?.*$/, "").replace(/\/[^\/]+$/, "/");
/******/ 		__webpack_require__.p = scriptUrl;
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/jsonp chunk loading */
/******/ 	(() => {
/******/ 		__webpack_require__.b = document.baseURI || self.location.href;
/******/ 		
/******/ 		// object to store loaded and loading chunks
/******/ 		// undefined = chunk not loaded, null = chunk preloaded/prefetched
/******/ 		// [resolve, reject, Promise] = chunk loading, 0 = chunk loaded
/******/ 		var installedChunks = {
/******/ 			"main": 0
/******/ 		};
/******/ 		
/******/ 		// no chunk on demand loading
/******/ 		
/******/ 		// no prefetching
/******/ 		
/******/ 		// no preloaded
/******/ 		
/******/ 		// no HMR
/******/ 		
/******/ 		// no HMR manifest
/******/ 		
/******/ 		// no on chunks loaded
/******/ 		
/******/ 		// no jsonp function
/******/ 	})();
/******/ 	
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval-source-map devtool is used.
/******/ 	var __webpack_exports__ = __webpack_require__("./kotlin/composeApp.mjs");
/******/ 	
/******/ 	return __webpack_exports__;
/******/ })()
;
});