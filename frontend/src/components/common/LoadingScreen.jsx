import { motion } from 'framer-motion'

function LoadingScreen() {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-neutral-50">
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        className="text-center"
      >
        <motion.div
          animate={{
            scale: [1, 1.1, 1],
            opacity: [0.5, 1, 0.5]
          }}
          transition={{
            duration: 1.5,
            repeat: Infinity,
            ease: "easeInOut"
          }}
          className="text-3xl font-bold mb-4 text-primary-800"
        >
          <span className="text-primary-500">Aurifex</span>Connect
        </motion.div>
        
        <div className="flex space-x-2 justify-center">
          <motion.div
            animate={{
              y: [0, -10, 0],
            }}
            transition={{
              duration: 0.6,
              repeat: Infinity,
              repeatDelay: 0.1,
              ease: "easeInOut",
            }}
            className="w-3 h-3 bg-primary-600 rounded-full"
          />
          <motion.div
            animate={{
              y: [0, -10, 0],
            }}
            transition={{
              duration: 0.6,
              repeat: Infinity,
              repeatDelay: 0.1,
              ease: "easeInOut",
              delay: 0.2,
            }}
            className="w-3 h-3 bg-primary-600 rounded-full"
          />
          <motion.div
            animate={{
              y: [0, -10, 0],
            }}
            transition={{
              duration: 0.6,
              repeat: Infinity,
              repeatDelay: 0.1,
              ease: "easeInOut",
              delay: 0.4,
            }}
            className="w-3 h-3 bg-primary-600 rounded-full"
          />
        </div>
        
        <p className="mt-4 text-sm text-neutral-500">Loading your finances...</p>
      </motion.div>
    </div>
  )
}

export default LoadingScreen