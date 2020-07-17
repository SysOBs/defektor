# TODO

- [X] Find out why Request arrives empty @Controller and fix it
- [ ] Create new submodule (injectors) for future pluginization
- [ ] Move HoleyBoat to injectors module
- [ ] Replace app.ijk with the model objects generated from spec (inside server)
- [ ] Select storage back-end (SQLite? MapDD? Redis? MongoDB?)
- [ ] Refine interface to parameterize the ijk's (how would we send fault type and location for holeyboat?)
	- [ ] Are targets generic enough to accommodate? 
- [ ] Define slave API
	- [ ] What are slaves? SSH machines? Kubernetes cluster? SSH + Docker?	
- [ ] Deal with containerization when we get to the first functioning version
