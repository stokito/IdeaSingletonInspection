This inspection reports about (probably) inappropriate use of Singleton pattern [http://en.wikipedia.org/wiki/Singleton_pattern].
Just write getInstance() method, and this inspection will check this class like a Singleton.
Singleton class should be checked for next errors:
* Class should be final.
* Class should have private constructor.
* getInstance() method must be public and static and return instance of its class.

Please see http://www.jpatterns.org/ for patterns annotations.