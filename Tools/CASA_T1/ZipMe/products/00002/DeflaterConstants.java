//

package net.sf.zipme; 

 

interface  DeflaterConstants {
	
	
	boolean DEBUGGING = false;

	
	int STORED_BLOCK = 0;

	
	int STATIC_TREES = 1;

	
	int DYN_TREES = 2;

	
	int PRESET_DICT = 0x20;

	
	int DEFAULT_MEM_LEVEL = 8;

	
	int MAX_MATCH = 258;

	
	int MIN_MATCH = 3;

	
	int MAX_WBITS = 15;

	
	int WSIZE = 1 << MAX_WBITS;

	
	int WMASK = WSIZE - 1;

	
	int HASH_BITS = DEFAULT_MEM_LEVEL + 7;

	
	int HASH_SIZE = 1 << HASH_BITS;

	
	int HASH_MASK = HASH_SIZE - 1;

	
	int HASH_SHIFT = (HASH_BITS + MIN_MATCH - 1) / MIN_MATCH;

	
	int MIN_LOOKAHEAD = MAX_MATCH + MIN_MATCH + 1;

	
	int MAX_DIST = WSIZE - MIN_LOOKAHEAD;

	
	int PENDING_BUF_SIZE = 1 << (DEFAULT_MEM_LEVEL + 8);

	
	int MAX_BLOCK_SIZE = Math.min(65535, PENDING_BUF_SIZE - 5);

	
	int DEFLATE_STORED = 0;

	
	int DEFLATE_FAST = 1;

	
	int DEFLATE_SLOW = 2;

	
	int GOOD_LENGTH[] = { 0, 4, 4, 4, 4, 8, 8, 8, 32, 32 };

	
	int MAX_LAZY[] = { 0, 4, 5, 6, 4, 16, 16, 32, 128, 258 };

	
	int NICE_LENGTH[] = { 0, 8, 16, 32, 16, 32, 128, 128, 258, 258 };

	
	int MAX_CHAIN[] = { 0, 4, 8, 32, 16, 32, 128, 256, 1024, 4096 };

	
	int COMPR_FUNC[] = { 0, 1, 1, 1, 1, 2, 2, 2, 2, 2 };


}
