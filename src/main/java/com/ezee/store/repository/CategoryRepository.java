package com.ezee.store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezee.store.config.JdbcConfig;
import com.ezee.store.dto.CategoryDTO;
import com.ezee.store.exception.ErrorCode;
import com.ezee.store.exception.ServiceException;

@Repository
public class CategoryRepository {
	@Autowired
	private JdbcConfig jdbcConfig;
	
	public static final Logger LOGGER = LogManager.getLogger("com.ezee.store.repository");
	public boolean existsById(int id) {
	    DataSource dataSource = jdbcConfig.getConnection();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    boolean exists = false;

	    try {
	        connection = dataSource.getConnection();
	        String query = "SELECT COUNT(category_id) FROM category WHERE category_id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, id);

	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            exists = count > 0;
	        }
	    } catch (SQLException e) {
	        LOGGER.error("Error in existsById: {}/n, data: {}", e.getMessage(), e);
	    } finally {
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return exists;
	}
	
	public List<CategoryDTO> fetchAllCustomer() {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		Statement statement = null;
		List<CategoryDTO> list = jdbcConfig.getList();
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			String query = "SELECT category_id, category_name, category_description, updated_at from category";
			resultSet = statement.executeQuery(query);

			for (; resultSet.next();) {
				CategoryDTO categoryDto=new CategoryDTO();
				categoryDto.setCategoryId(resultSet.getInt("category_id"));
				categoryDto.setCategoryName(resultSet.getString("category_name"));
				categoryDto.setCategoryDescription(resultSet.getString("category_description"));
				categoryDto.setUpdatedAt(resultSet.getDate("updated_at"));
				list.add(categoryDto);
			}
			System.out.println("executed category repo. fetchall");

		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}

		return list;
	}
	public CategoryDTO fetchById(int id) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		CategoryDTO categoryDto = null;

			try {
				connection = dataSource.getConnection();

				String query = "SELECT category_id, category_name, category_description, updated_at from category where category_id=?";
				
				statement = connection.prepareStatement(query);
				statement.setInt(1, id);

				resultSet = statement.executeQuery();
				

				if (resultSet != null) {
					if (resultSet.next()) {
					   categoryDto=new CategoryDTO();
						categoryDto.setCategoryId(resultSet.getInt("category_id"));
						categoryDto.setCategoryName(resultSet.getString("category_name"));
						categoryDto.setCategoryDescription(resultSet.getString("category_description"));
						categoryDto.setUpdatedAt(resultSet.getDate("updated_at"));
						
					}
						 else {
								throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
							}				
						}

						System.out.println("fetchbyId executed.");	
					} catch (SQLException e) {
						LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
					} finally {
						if (connection != null) {
							try {
								connection.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
						}

						if (statement != null) {
							try {
								statement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
						if (resultSet != null) {
							try {
								resultSet.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
					}
					
					return categoryDto;
				}
	public int delete(int id) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int affected = 0;

		try {
			connection = dataSource.getConnection();
			String query = "DELETE FROM category WHERE category_id = ?";

			statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			affected = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.info("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return affected;
	}
	public int update(CategoryDTO categoryDto) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int executeUpdate=0;
		
		try {
			connection = dataSource.getConnection();
			String query = "UPDATE category SET  category_name=?, category_description=? where category_id=?";
			
			statement = connection.prepareStatement(query);
			statement.setString(1, categoryDto.getCategoryName());
			statement.setString(2, categoryDto.getCategoryDescription());
			statement.setInt(3, categoryDto.getCategoryId());
			executeUpdate = statement.executeUpdate();
			System.out.println("update executed.");

		} catch (SQLException e) {
			LOGGER.info("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return executeUpdate;
		
	}
	public int addCategory(CategoryDTO categoryDto) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int executeUpdate = 0;

		try {
			if (existsById(categoryDto.getCategoryId())) {
	            throw new ServiceException(ErrorCode.ID_ALREADY_EXISTS);
	        }
			connection = dataSource.getConnection();

			String query = "INSERT INTO category (category_id, category_name, category_description, updated_at) VALUES ( ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, categoryDto.getCategoryId());
			statement.setString(2, categoryDto.getCategoryName());
			statement.setString(3, categoryDto.getCategoryDescription());
			statement.setDate(4, categoryDto.getUpdatedAt());
			executeUpdate = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
			throw new ServiceException(ErrorCode.DATABASE_ERROR, e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return executeUpdate;
	}
}

				
				
			
		
	


