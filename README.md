<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.

# jahia-open-store
Community (Open Source) alternative to "official" JahiaAppStore modules

Goals:
======
1) First goal is to allow, from a Jahia "Community" instance, 
    to download all the JC modules (on https://github.com/JahiaCommunity) 
     from the Jahia Admin (exactly as default Jahia modules)
2) Implement an altenative website templates to expose these modules on a 'Vitrine' Jahia website (throught an EDP in main module + sample templateSet)

Current technical implementation:
=================================
Use the Github API V4 (in GraphQL) to retrieve Github JC modules infos:
* implementation of "contentFolder.modulesList" view compatible with Jahia Modules Admin requests
* implementation of a GithubProxy to download the modules (because Github as only 'temporary' url for Assets download), 
   with a call in a wrapper WHEN the modules is downloaded from Jahia Admin.

TODO V1:
========
* implement module "info" page correctly (from admin)
* auto-config the JC appstore in Jahia JCR (because Jahia Community versions doesn't allow to configure it manually from admin)


Next -> V2: 
===========
Implement the main module as a "service" and allow other "extension/fragment" modules to implement several sources (Github, Gitlab, Bitbucket,...)
