var searchData=
[
  ['whichside',['WhichSide',['../enumpz2015_1_1habits_1_1rmm_1_1activity_1_1login__and__registration_1_1_connection_task_1_1_which_side.html',1,'pz2015::habits::rmm::activity::login_and_registration::ConnectionTask']]],
  ['widget',['widget',['../jquery_8js.html#a4af68ff81d322a2563861409a79ff5bf',1,'widget(&quot;ui.mouse&quot;,{options:{cancel:&quot;:input,option&quot;, distance:1, delay:0}, _mouseInit:function(){var d=this;this.element.bind(&quot;mousedown.&quot;+this.widgetName, function(e){return d._mouseDown(e)}).bind(&quot;click.&quot;+this.widgetName, function(e){if(true===b.data(e.target, d.widgetName+&quot;.preventClickEvent&quot;)){b.removeData(e.target, d.widgetName+&quot;.preventClickEvent&quot;);e.stopImmediatePropagation();return false}});this.started=false}, _mouseDestroy:function(){this.element.unbind(&quot;.&quot;+this.widgetName)}, _mouseDown:function(f){if(a){return}(this._mouseStarted &amp;&amp;this._mouseUp(f));this._mouseDownEvent=f;var e=this, g=(f.which==1), d=(typeof this.options.cancel==&quot;string&quot;&amp;&amp;f.target.nodeName?b(f.target).closest(this.options.cancel).length:false);if(!g||d||!this._mouseCapture(f)){return true}this.mouseDelayMet=!this.options.delay;if(!this.mouseDelayMet){this._mouseDelayTimer=setTimeout(function(){e.mouseDelayMet=true}, this.options.delay)}if(this._mouseDistanceMet(f)&amp;&amp;this._mouseDelayMet(f)){this._mouseStarted=(this._mouseStart(f)!==false);if(!this._mouseStarted){f.preventDefault();return true}}if(true===b.data(f.target, this.widgetName+&quot;.preventClickEvent&quot;)){b.removeData(f.target, this.widgetName+&quot;.preventClickEvent&quot;)}this._mouseMoveDelegate=function(h){return e._mouseMove(h)};this._mouseUpDelegate=function(h){return e._mouseUp(h)};b(document).bind(&quot;mousemove.&quot;+this.widgetName, this._mouseMoveDelegate).bind(&quot;mouseup.&quot;+this.widgetName, this._mouseUpDelegate);f.preventDefault();a=true;return true}, _mouseMove:function(d){if(b.browser.msie &amp;&amp;!(document.documentMode &gt;=9)&amp;&amp;!d.button){return this._mouseUp(d)}if(this._mouseStarted){this._mouseDrag(d);return d.preventDefault()}if(this._mouseDistanceMet(d)&amp;&amp;this._mouseDelayMet(d)){this._mouseStarted=(this._mouseStart(this._mouseDownEvent, d)!==false);(this._mouseStarted?this._mouseDrag(d):this._mouseUp(d))}return!this._mouseStarted}, _mouseUp:function(d){b(document).unbind(&quot;mousemove.&quot;+this.widgetName, this._mouseMoveDelegate).unbind(&quot;mouseup.&quot;+this.widgetName, this._mouseUpDelegate);if(this._mouseStarted){this._mouseStarted=false;if(d.target==this._mouseDownEvent.target){b.data(d.target, this.widgetName+&quot;.preventClickEvent&quot;, true)}this._mouseStop(d)}return false}, _mouseDistanceMet:function(d){return(Math.max(Math.abs(this._mouseDownEvent.pageX-d.pageX), Math.abs(this._mouseDownEvent.pageY-d.pageY))&gt;=this.options.distance)}, _mouseDelayMet:function(d){return this.mouseDelayMet}, _mouseStart:function(d){}, _mouseDrag:function(d){}, _mouseStop:function(d){}, _mouseCapture:function(d){return true}})})(jQuery):&#160;jquery.js'],['../jquery_8js.html#ac118a4c29a2ace98e4c956a7e504390d',1,'Widget():&#160;jquery.js']]],
  ['window',['window',['../jquery_8js.html#a3a1b34e910bfab09201cf4cd802439ea',1,'jquery.js']]],
  ['writecookie',['writeCookie',['../resize_8js.html#ad0822459a7d442b8c5e4db795d0aabb4',1,'resize.js']]]
];
